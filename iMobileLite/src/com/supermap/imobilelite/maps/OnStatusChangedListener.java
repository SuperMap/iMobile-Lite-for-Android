package com.supermap.imobilelite.maps;

interface OnStatusChangedListener {
    void onStatusChanged(Object sourceObject, STATUS status);

    enum STATUS {
        INITIALIZED("source object initialized.", 1), INITIALIZED_FAILED("source object initialized failed.", 0), LAYER_REFRESH("layerView_refreshed.", 3);
        private String description;
        private int value;

        private STATUS(String description, int value) {
            this.description = description;
            this.value = value;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getValue() {
            return this.value;
        }

        public String toString() {
            if (this.value == 1) {
                return "INITIALIZED:" + description;
            } else if (this.value == 2) {
                return "LAYER_REFRESH:" + description;
            } else {
                return "INITIALIZED_FAILED:" + description;
            }
        }
    }
}
