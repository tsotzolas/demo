/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


import lombok.Getter;
import lombok.Setter;
import model.Tblemployee;
import model.Tbluser;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import utils.SSLFix;
import utils.StringUtils;
import org.primefaces.model.file.UploadedFile;


/**
 * @author takis
 */

@Named("userpreferencesBean")
@Getter
@Setter
@SessionScoped
public class UserpreferencesBean implements Serializable {

    //Login Bean
    @Inject
    private LoginBean loginBean;

    private Tblemployee employee = new Tblemployee();
    private Tbluser user = new Tbluser();

    private InputStream avatarInputStream = null;
    private byte[] avatarByteArray;

    private StreamedContent avatarImage;

    private File imageFile;

    private CroppedImage croppedImage = new CroppedImage();
    private InputStream in = null;

    private String imageFilePath;
    private String filename;

    private UploadedFile uploadedFile;

    private String tempFolder;
    private String serverURL;


    @PostConstruct
    public void init() {
        employee = loginBean.getUser().getIdEmployee();
        user = employee.getTblusers();

        //Βρίσκουμε το temp folder του συστήματος.
        //Αυτό το χρειζόμαστε γιατί στο crop της φωτογραφίας πρέπει να έχουμε αποθηκευμένη την φωτογραφία σε filesystem.
        tempFolder = System.getProperty("jboss.server.temp.dir");
    }


    public void clear() {

        imageFilePath = null;
        avatarImage = null;
        croppedImage = new CroppedImage();
        System.out.println(croppedImage);

    }

    public void saveEmployee() {
        FacesMessage message = new FacesMessage();

        if (employee != null) {
            db.dbTransactions.updateObject(employee);
        }
        if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {

            FacesMessage msg = new FacesMessage("Successful Update", (employee.getEmployeeLname()));
            FacesContext.getCurrentInstance().addMessage(null, msg);

             }
    }

    public void saveUser() {
        FacesMessage message = new FacesMessage();

        if (user != null) {
            db.dbTransactions.updateObject(user);
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to save", user.getUsername());
        }
        if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {

            FacesMessage msg = new FacesMessage("Successful Update", (user.getUsername()));
            FacesContext.getCurrentInstance().addMessage(null, msg);

             }
    }


    public void oncapture(CaptureEvent captureEvent) {
        String filename = loginBean.getUsername() + "_avatar.jpg";
        avatarByteArray = captureEvent.getData();

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String newFileName = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "demo" +
                File.separator + "images" + File.separator + "photocam" + File.separator + filename + ".jpeg";

        File tempFile = new File("../temp_file.jpg");

        try {

            // Initialize a pointer
            // in file using OutputStream
            OutputStream os = new FileOutputStream(tempFile);

            // Starts writing the bytes in it
            os.write(avatarByteArray);
            System.out.println("Successfully" + " byte inserted");

            // Close the file
            os.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(newFileName));
            imageOutput.write(avatarByteArray, 0, avatarByteArray.length);
            imageOutput.close();
        } catch (IOException e) {
            throw new FacesException("Error in writing captured image.", e);
        }
    }


    public void handleFileUpload(FileUploadEvent event) throws IOException {

        uploadedFile = event.getFile();
        File tempfile = new File(tempFolder);

        String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
        Path file = Files.createTempFile(tempfile.toPath(), "sample_", "." + extension);
        Files.copy(uploadedFile.getInputStream(), file, StandardCopyOption.REPLACE_EXISTING);

        imageFilePath = file.getFileName().toString();
        FacesMessage message = new FacesMessage("Succesful", imageFilePath + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Μέθοδος όπου κάνει crop την φωτογραφία και ταυτόχρονα την μικραίνει σε μέγεθος
     * @throws IOException
     */
    public void crop() throws IOException {
        SSLFix.execute();
        avatarImage = new DefaultStreamedContent();
//        avatarImage = new DefaultStreamedContent(new InputStream(croppedImage.getBytes()));

        avatarImage = DefaultStreamedContent.builder()
                .contentType("image/png")
                .stream(() -> {
                    try {
                        return new ByteArrayInputStream(croppedImage.getBytes());
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .build();

        //Κάνουμε resize την φωτογραφία
        InputStream initialStream = new ByteArrayInputStream(croppedImage.getBytes());

        in = new ByteArrayInputStream(croppedImage.getBytes());
//        InputStream finalStream = Utils.GenericUtils.resizeImage(initialStream,croppedImage.getOriginalFilename(),100,100);
//        in = Utils.GenericUtils.resizeImage(initialStream,croppedImage.getOriginalFilename(),100,100);
//        avatarByteArray = IOUtils.toByteArray(finalStream);
//        avatarByteArray = IOUtils.toByteArray(in);
        avatarByteArray = croppedImage.getBytes();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Cropping finished."));
    }


    /**
     * Μέθοδος που αποθηκεύει το avatar του χρήστη
     */
    public void saveAvatar() {

        Tbluser user = loginBean.getUser();

        //Μετατρέπουμε την φωτογραφία του χρήστη σε base64 string για να την αποθηκεύσουμε μέσα στη βάση.
//        String base64String = StringUtils.bytesToBase64String(avatarByteArray);
        String base64String = StringUtils.inputStreamToBase64String(in);

        if (base64String.length() > 65500) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "The image is too big", "Please add smaller image"));
        } else {
            user.setAvatar(base64String);
            db.dbTransactions.updateObject(user);
        }

        if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
             } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "The insert failed", null));
        }

    }


    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }


    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("User update canceled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}

