package soadev.taskflowadapter.view.backing;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

public class SampleForm implements Serializable{
    private static final long serialVersionUID = 1L;

    public SampleForm() {
        super();
    }
    
    public Map<String, Object> getSampleParameterMap(){
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("companyId", 1L);
        return parameterMap;
    }

}
