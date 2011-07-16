package reflectionspike.model;

public class B {

    private Long c;
    private String d;
    private Long y;

    public B() {
    }

    public B(Long c, String d, Long y) {
        this.c = c;
        this.d = d;
        this.y = y;
    }

    public Long getC() {
        return c;
    }

    public void setC(Long c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final B other = (B) obj;
        if (this.c != other.c && (this.c == null || !this.c.equals(other.c))) {
            return false;
        }
        if ((this.d == null) ? (other.d != null) : !this.d.equals(other.d)) {
            return false;
        }
        if (this.y != other.y && (this.y == null || !this.y.equals(other.y))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.c != null ? this.c.hashCode() : 0);
        hash = 97 * hash + (this.d != null ? this.d.hashCode() : 0);
        hash = 97 * hash + (this.y != null ? this.y.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "B{" + "c=" + c + "d=" + d + "y=" + y + '}';
    }

}
