/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reflectionspike.model;

import reflectionspike.validator.ValidadeNotEmpty;
import reflectionspike.validator.ValidateBiggerThan;
import reflectionspike.validator.ValidateNotNull;

/**
 *
 * @author lucastorri
 */
public class A {

    @ValidateNotNull
    @ValidateBiggerThan(value = 10)
    private Long a;
    @ValidadeNotEmpty
    @ValidateNotNull
    private String b;
    private String x;

    public A(Long a, String b, String x) {
        this.a = a;
        this.b = b;
        this.x = x;
    }

    public A() {
    }

    public Long getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public void setA(Long a) {
        this.a = a;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final A other = (A) obj;
        if (this.a != other.a && (this.a == null || !this.a.equals(other.a))) {
            return false;
        }
        if ((this.b == null) ? (other.b != null) : !this.b.equals(other.b)) {
            return false;
        }
        if ((this.x == null) ? (other.x != null) : !this.x.equals(other.x)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.a != null ? this.a.hashCode() : 0);
        hash = 37 * hash + (this.b != null ? this.b.hashCode() : 0);
        hash = 37 * hash + (this.x != null ? this.x.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "A{" + "a=" + a + "b=" + b + "x=" + x + '}';
    }
    
}
