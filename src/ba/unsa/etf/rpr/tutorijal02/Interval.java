package ba.unsa.etf.rpr.tutorijal02;

public class Interval {

    private double pocetna, krajnja;
    private boolean prip_poc, prip_kraj;

    public Interval(double poc, double kraj, boolean pripadnost_poc, boolean pripadnost_kraj) {
        if (kraj < poc) throw new IllegalArgumentException();
        pocetna = poc; krajnja = kraj;
        prip_poc = pripadnost_poc; prip_kraj = pripadnost_kraj;
        // ovo ustvari predstalja razliku izmedu intervala (2,5) i [2,5]
    }

    public Interval() {
        pocetna = 0; krajnja = 0;
        prip_poc = false; prip_kraj = false;
    }

    public boolean isNull() {
        return pocetna == 0 && krajnja == 0 && !prip_poc && !prip_kraj;
    }

    public boolean isIn(double tacka) {
        if (prip_poc && prip_kraj) return tacka >= pocetna && tacka <= krajnja;
        else if (prip_poc) return tacka >= pocetna && tacka < krajnja;
        else if (prip_kraj) return tacka > pocetna && tacka <= krajnja;
        else return  tacka > pocetna && tacka < krajnja;
    }

    public Interval intersect(Interval interval) {
        Interval presjek;
        if (pocetna < interval.pocetna && krajnja > interval.pocetna) presjek = new Interval(interval.pocetna, krajnja, interval.prip_poc, prip_kraj);
        else if (pocetna >= interval.pocetna && pocetna <= interval.krajnja) presjek = new Interval(pocetna, interval.krajnja, prip_poc, interval.prip_kraj);
        else presjek = new Interval();
        return presjek;
    }

    public static Interval intersect(Interval i1, Interval i2) {
        Interval presjek;
        if (i1.pocetna >= i2.pocetna && i1.pocetna <= i2.krajnja) { // provjerava da li je prvi interval manji od drugog, tj. pocetak prvog manji od pocetka drugog
            if (i1.krajnja >= i2.pocetna && i1.krajnja <= i2.krajnja) presjek = new Interval(i1.pocetna, i1.krajnja, i1.prip_poc, i1.prip_kraj);//ako se i1 nalazi u i2
            else presjek = new Interval(i1.pocetna, i2.krajnja, i1.prip_poc, i2.prip_kraj); // ako je pocetak i1 u intervalu i2, ali kraj i1 je izvan i2
        }
        else if (i2.pocetna >= i1.pocetna && i2.pocetna <= i2.krajnja) {
            if (i2.krajnja >= i1.pocetna && i2.krajnja <= i1.krajnja) presjek = new Interval(i2.pocetna, i2.krajnja, i2.prip_poc, i2.prip_kraj);
            else presjek = new Interval(i2.pocetna, i1.krajnja, i2.prip_poc, i1.prip_kraj); // isto kao 2. slucaj iz prvog uslova
        }
        else presjek = new Interval();
        return presjek;
    }

    @Override
    public boolean equals(Object o) {
        Interval i = (Interval) o;
        return i.pocetna == pocetna && i.krajnja == krajnja && i.prip_poc == prip_poc && i.prip_kraj == prip_kraj;
    }

    @Override
    public String toString() {
        String interval = "";
        if (prip_poc) {
            interval += "["; interval += pocetna; interval += ","; interval += krajnja;
            if (prip_kraj) interval += "]";
            else interval += ")";
        }
        else {
            if (!prip_kraj && pocetna == 0 && krajnja == 0) return interval += "()"; //kada ni pocetna ni krajnja granica ne pripadaju, a interval je od 0 do 0, tj. konstruktor bez parametara
            interval += "("; interval += pocetna; interval += ","; interval += krajnja;
            if (prip_kraj) interval += "]";
            else interval += ")";
        }
        return interval;
    }
}
