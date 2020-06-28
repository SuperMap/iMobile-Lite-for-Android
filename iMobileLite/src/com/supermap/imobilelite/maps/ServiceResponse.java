package com.supermap.imobilelite.maps;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

class ServiceResponse {
    private JSONHelper helper = new JSONHelper();
    public Info info;
    public JSONObject serviceResponse;

    public ServiceResponse() {
        this.info = new Info();
    }

    public ServiceResponse(JSONObject serviceResponse) {
        this.serviceResponse = serviceResponse;
        this.info = new Info(this.helper.getJSONObject("info", serviceResponse));
    }

    JSONHelper getHelper() {
        return this.helper;
    }

    public class Info {
        public Copyright copyright;
        public int statusCode = -1;
        public List<String> messages = new ArrayList();

        public Info() {
        }

        Info(JSONObject infoNode) {
            this.copyright = new Copyright(ServiceResponse.this.helper.getJSONObject("copyright", infoNode));
            this.statusCode = ServiceResponse.this.helper.getInt("statuscode", infoNode);
            JSONArray messArray = ServiceResponse.this.helper.getJSONArray("messages", infoNode);
            for (int i = 0; i < messArray.length(); i++)
                this.messages.add(ServiceResponse.this.helper.getString(i, messArray));
        }

        public class Copyright {
            public String text = "";
            public String imageUrl = "";
            public String imageAltText = "";

            Copyright(JSONObject copyrightNode) {
                this.text = ServiceResponse.this.helper.getString("text", copyrightNode);
                this.imageUrl = ServiceResponse.this.helper.getString("imageUrl", copyrightNode);
                this.imageAltText = ServiceResponse.this.helper.getString("imageAltText", copyrightNode);
            }
        }
    }
}