package com.ilku1297.proxy;

import lombok.Data;

@Data
public class JProxy{
    public JProxy(String h, String p){
        host = h;
        port = p;
    }
    private String host, port;

    private boolean isBusy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        JProxy jProxy = (JProxy) o;

        if (host != null ? !host.equals(jProxy.host) : jProxy.host != null) return false;
        return port != null ? port.equals(jProxy.port) : jProxy.port == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (host != null ? host.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        return result;
    }
}
