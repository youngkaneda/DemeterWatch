package ifpb.gpes;

import java.util.Objects;

/**
 *
 * @author Juan
 */
public class No {

    //TODO: atualizar os nomes dos atributos
    private String a;
    private String m;
    private String rt;
    private String c;
    private String m1;
    private String mi;
    private String inv;

    public No() {
    }

    private No(String a, String m, String rt, String c, String m1, String mi) {
        this.a = a;
        this.m = m;
        this.rt = rt;
        this.c = c;
        this.m1 = m1;
        this.mi = mi;
    }

    public static No of(String a, String m, String rt, String c, String m1, String mi) {
        return new No(a, m, rt, c, m1, mi);
    }

    public String getInv() {
        return inv;
    }

    public void setInv(String inv) {
        this.inv = inv;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getM1() {
        return m1;
    }

    public void setM1(String m1) {
        this.m1 = m1;
    }

    public String getMi() {
        return mi;
    }

    public void setMi(String mi) {
        this.mi = mi;
    }

    @Override
    public String toString() {
        return "No{" + "a=" + a + ", m=" + m + ", rt=" + rt + ", c=" + c + ", m1=" + m1 + ", mi=" + mi + ", inv=" + inv + '}';
    }

    public String callGraph() {
        return "<" + a + ", " + m + ", " + rt + ", " + c + ", " + m1 + ", " + mi + ">";
    }
    
    public String noOf(){
        return "No.of(\""+a + "\", \"" + m + "\",\"" + rt + "\",\"" + c + "\",\"" + m1 + "\",\"" + mi + "\"),";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.a);
        hash = 89 * hash + Objects.hashCode(this.m);
        hash = 89 * hash + Objects.hashCode(this.rt);
        hash = 89 * hash + Objects.hashCode(this.c);
        hash = 89 * hash + Objects.hashCode(this.m1);
        hash = 89 * hash + Objects.hashCode(this.mi);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final No other = (No) obj;
        if (!Objects.equals(this.a, other.a)) {
            return false;
        }
        if (!Objects.equals(this.m, other.m)) {
            return false;
        }
        if (!Objects.equals(this.rt, other.rt)) {
            return false;
        }
        if (!Objects.equals(this.c, other.c)) {
            return false;
        }
        if (!Objects.equals(this.m1, other.m1)) {
            return false;
        }
        if (!Objects.equals(this.mi, other.mi)) {
            return false;
        }
        return true;
    }

}
