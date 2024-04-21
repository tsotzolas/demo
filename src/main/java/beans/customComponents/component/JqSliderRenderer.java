package beans.customComponents.component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@FacesRenderer(componentFamily = JqSlider.COMPONENT_FAMILY, rendererType = JqSlider.DEFAULT_RENDERER)
public class JqSliderRenderer extends Renderer {

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        if (!component.isRendered()) {
            return;
        }
        JqSlider jqSlider = (JqSlider) component;

        Map<String, Object> attributes = component.getAttributes();
        Date mindate = (Date) attributes.get("mindate");
        Date maxdate = (Date) attributes.get("maxdate");
        Date mindefaultdate = (Date) attributes.get("mindefaultdate");
        Date maxdefaultdate = (Date) attributes.get("maxdefaultdate");
        String inputHiddenId = (String) attributes.get("for");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("div", component);
        writer.startElement("dl", component);
        writer.writeAttribute("id", jqSlider.getClientId(), null);
        writer.writeAttribute("class", "iThing", null);
        writer.endElement("dl");
        writer.endElement("div");


        String valValue = findIdString(jqSlider.getClientId())+":"+inputHiddenId;
        String valId = jqSlider.getClientId() ;

//        writer.startElement("input", jqSlider);
//        writer.writeAttribute("type", "hidden", null);
//        writer.writeAttribute("id", jsId(valValue), null);
//        writer.writeAttribute("value", jsId(valValue), null);
//        writer.endElement("input");


        String mindateString = sdf.format(mindate);
        String maxdateString = sdf.format(maxdate);
        String mindefaultdateString = sdf.format(mindefaultdate);
        String maxdefaultdateString = sdf.format(maxdefaultdate);

        StringBuilder callString = new StringBuilder();
        callString.append("rangeSlider(");
        callString.append("'");
        callString.append(mindateString);
        callString.append("','");
        callString.append(maxdateString);
        callString.append("','");
        callString.append(valId);
        callString.append("','");
        callString.append(valValue);
        callString.append("','");
        callString.append(mindefaultdateString);
        callString.append("','");
        callString.append(maxdefaultdateString);
        callString.append("')");


        writer.startElement("script", null);
        writer.writeAttribute("type", "text/javascript", null);
        writer.writeText(callString.toString(),null);
        writer.endElement("script");
    }

    /**
     *Επιστρεφει το preffix του id της φορμας.
     * @return
     */
    private String findIdString(String inputString){
        inputString = inputString.substring(0,inputString.lastIndexOf(":"));
        return inputString;
    }
}

