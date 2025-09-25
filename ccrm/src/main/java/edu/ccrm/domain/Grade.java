package edu.ccrm.domain;

public enum Grade {
    S(10), A(9), B(8), C(7), D(6), E(5), F(0);

    private final int points;
    Grade(int points) { this.points = points; }
    public int getPoints() { return points; }

    public static Grade fromPercent(double p) {
        if (p >= 90) return S;
        if (p >= 80) return A;
        if (p >= 70) return B;
        if (p >= 60) return C;
        if (p >= 50) return D;
        if (p >= 40) return E;
        return F;
    }
}

