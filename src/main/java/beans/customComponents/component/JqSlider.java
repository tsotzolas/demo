package beans.customComponents.component;

import beans.customComponents.util.Resource;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIInput;

@FacesComponent(value = JqSlider.COMPONENT_TYPE)
public class JqSlider extends UIInput {

//    public JqSlider() {

//        setRendererType(DEFAULT_RENDERER);
//    }

    /**
     * Set renderer to {@link #DEFAULT_RENDERER}.
     */
    private void setDefaultRenderer()
    {
        setRendererType(DEFAULT_RENDERER);
    }

    /**
     * Component type.
     */
    public static final String COMPONENT_TYPE = "jqSliderComponent";

    /**
     * Component family.
     */
    public static final String COMPONENT_FAMILY = "jqSliderComponent";

    /**
     * Default renderer.
//     */
    public static final String DEFAULT_RENDERER = "jqSliderComponent";

    /**
     * {@link #setDefaultRenderer() Sets default renderer} and adds scripts to head.
     */
    public JqSlider()
    {
        setDefaultRenderer();
        Resource.addJQueryToHead();
        Resource.addScriptToHead("jqrangeslider", "jQAllRangeSliders-min.js");
        Resource.addScriptToHead("jqrangeslider", "jqRangeSliderCode1.js");
        Resource.addScriptToHead("jqrangeslider", "jquery.mousewheel.min.js");
        Resource.addCSSToHead();
//        Script localeScript = LocaleScript.locateTranslation();
//        if (localeScript != null) {
//            Resource.addScriptToHead(localeScript);
//        }
    }



    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }
}

