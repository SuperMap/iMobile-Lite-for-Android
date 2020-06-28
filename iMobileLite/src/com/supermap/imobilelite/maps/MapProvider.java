package com.supermap.imobilelite.maps;

class MapProvider {
    public static MapProvider REST = new MapProvider("REST");
    private final String value;

    MapProvider(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MapProvider other = (MapProvider) obj;
        if (this.value == null) {
            if (other.value != null)
                return false;
        } else if (!this.value.equals(other.value))
            return false;
        return true;
    }
    
}