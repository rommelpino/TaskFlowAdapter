package com.soadev.view;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import java.math.BigDecimal;

import java.net.URLDecoder;

import java.util.HashMap;
import java.util.Map;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.controller.internal.metadata.TaskFlowDefinition;
import oracle.adf.controller.internal.metadata.TaskFlowInputParameter;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.event.RegionNavigationEvent;

import org.apache.myfaces.trinidad.util.ComponentReference;

import soadev.view.utils.ADFUtils;
import soadev.view.utils.JSFUtils;
import soadev.view.utils.TaskFlowUtils;


public class FragmentWrapperForm implements Serializable {
    private static final long serialVersionUID = 1L;
    private TaskFlowId taskFlowId;
    private transient Map<String, Object> parameterMap;
    private ComponentReference<RichCommandButton> launcherButton;


    public FragmentWrapperForm() {
    }

    private Object convert(String input, String type) {
        if (type == null) {
            return input;
        }
        if ("java.lang.Long".equals(type)) {
            return Long.valueOf(input);
        }
        if ("java.lang.Integer".equals(type)) {
            return Integer.valueOf(input);
        }
        if ("java.math.BigDecimal".equals(type)) {
            return new BigDecimal(input);
        }
        if ("java.lang.Boolean".equals(type)) {
            return Boolean.valueOf(input);
        }
        return input;
    }

    public TaskFlowId getDynamicTaskFlowId() {
        if (taskFlowId == null) {
            String taskFlow =
                (String)ADFUtils.getPageFlowScope().get("taskFlowId");
            try {
                taskFlow = URLDecoder.decode(taskFlow, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            System.out.println("taskFlow: " + taskFlow);
            taskFlowId = TaskFlowId.parse(taskFlow);
        }
        return taskFlowId;
    }

    public void regionNavigationListener(RegionNavigationEvent regionNavigationEvent) {
        String newViewId = regionNavigationEvent.getNewViewId();
        System.out.println("regionNavigationListener " + newViewId);
        if (newViewId == null) {
            //there is no turning back
            //trans committed or rolledback already
            JSFUtils.handleNavigation(null, "done");
        }
    }

    public Map<String, Object> getParameterMap() {
        if (parameterMap == null) {
            parameterMap =
                    (Map<String, Object>)ADFUtils.getPageFlowScope().get("parameterMap");
            if (parameterMap == null) {
                parameterMap = new HashMap<String, Object>();
                TaskFlowDefinition definition =
                    TaskFlowUtils.getTaskFlowDefinition(getDynamicTaskFlowId());
                if (definition == null ||
                    definition.getInputParameters() == null) {
                    return parameterMap;
                }
                String parameters =
                    (String)ADFUtils.getPageFlowScope().get("parameters");
                if (parameters != null && !";".equals(parameters)) {
                    String[] parameterArray = parameters.split(";");
                    for (String pair : parameterArray) {
                        String[] parameter = pair.split(":");
                        String name = parameter[0];
                        TaskFlowInputParameter param =
                            definition.getInputParameters().get(name);
                        if (param != null) {
                            Object value =
                                convert(parameter[1], param.getType());
                            parameterMap.put(name, value);
                        }

                    }
                }
            }
        }
        return parameterMap;
    }

    public void setLauncherButton(RichCommandButton launcherButton) {
        if (this.launcherButton == null) {
            this.launcherButton =
                    ComponentReference.newUIComponentReference(launcherButton);
        }
    }

    public RichCommandButton getLauncherButton() {
        return launcherButton == null ? null : launcherButton.getComponent();
    }
}
