package de.jpwinkler.daf.documenttagging.maxent.util;

public class CompositeKey4<K1, K2, K3, K4> {

    private K1 k1;
    private K2 k2;
    private K3 k3;
    private K4 k4;

    public CompositeKey4(final K1 k1, final K2 k2, final K3 k3, final K4 k4) {
        super();
        this.k1 = k1;
        this.k2 = k2;
        this.k3 = k3;
        this.k4 = k4;
    }

    public CompositeKey4() {
        // TODO Auto-generated constructor stub
    }

    public K1 getK1() {
        return k1;
    }

    public void setK1(final K1 k1) {
        this.k1 = k1;
    }

    public K2 getK2() {
        return k2;
    }

    public void setK2(final K2 k2) {
        this.k2 = k2;
    }

    public K3 getK3() {
        return k3;
    }

    public void setK3(final K3 k3) {
        this.k3 = k3;
    }

    public K4 getK4() {
        return k4;
    }

    public void setK4(final K4 k4) {
        this.k4 = k4;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((k1 == null) ? 0 : k1.hashCode());
        result = prime * result + ((k2 == null) ? 0 : k2.hashCode());
        result = prime * result + ((k3 == null) ? 0 : k3.hashCode());
        result = prime * result + ((k4 == null) ? 0 : k4.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CompositeKey4 other = (CompositeKey4) obj;
        if (k1 == null) {
            if (other.k1 != null) {
                return false;
            }
        } else if (!k1.equals(other.k1)) {
            return false;
        }
        if (k2 == null) {
            if (other.k2 != null) {
                return false;
            }
        } else if (!k2.equals(other.k2)) {
            return false;
        }
        if (k3 == null) {
            if (other.k3 != null) {
                return false;
            }
        } else if (!k3.equals(other.k3)) {
            return false;
        }
        if (k4 == null) {
            if (other.k4 != null) {
                return false;
            }
        } else if (!k4.equals(other.k4)) {
            return false;
        }
        return true;
    }


}