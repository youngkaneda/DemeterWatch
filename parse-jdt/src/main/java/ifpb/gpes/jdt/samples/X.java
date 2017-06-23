package ifpb.gpes.jdt.samples;

/**
 *
 * @author juan
 */
public class X {

    private D d = new D();
    private I i;
    
    public void testeOutro() {
        this.d.teste();
    }
    
//    public void m2() {
//        this.d.m1().getElements().remove(new A());
//    }

//    public void m2(String tst) {
//        this.d.m1().getElements().remove(new A());
//    }

//    public void m2(String tst, Integer a) {
//        this.d.m1().getElements().remove(new A());
//    }

    public void m3() {
        D d = new D(){
            public void testando(){
                System.out.println("oi");
                new A().getElements().add(null);
                
                D d2 = new D(){
                    public void testando5(){
                        new A().getElements().remove(null);
                    } 
                };
            }
            public int m4(){
                return 0;
            }
        };
        
        Runnable runner = ()->{ System.out.print("alo");
        new A().getElements().add(null);};
        i.semRetorno();
    }
}