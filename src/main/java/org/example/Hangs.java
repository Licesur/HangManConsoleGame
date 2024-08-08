package org.example;

public enum Hangs {
    FIRSTHANG("=============\n||/         |\n||\n||\n||\n||\n||\\"),
    SECONDHANG("=============\n||/         |\n||         (O)\n||\n||\n||\n||\\"),
    THIRDHANG("=============\n||/         |\n||         (O)\n||         / \\\n||         \\ /\n||\n||\\"),
    FOURTHHANG("=============\n||/         |\n||         (O)\n||         / \\\n||         \\ /\n||         /\n||\\"),
    FIFTHHANG("=============\n||/         |\n||         (O)\n||         / \\\n||         \\ /\n||         / \\\n||\\"),
    SIXTHHANG("=============\n||/         |\n||         (O)\n||         / \\\\\n||         \\ /\n||         / \\\n||\\"),
    SEVENTHHANG("=============\n||/         |\n||         (O)\n||        // \\\\\n||         \\ /\n||         / \\\n||\\"),
    HARDFIRST("\n||\n||\n||\n||\n||\\"),
    HARDSECOND("=============\n||/\n||\n||\n||\n||\n||\\"),
    HARDTHIRD("");

    private final String hang;

    public String getHang() {
        return this.hang;
    }

    Hangs(String s) {
        this.hang = s;
    }
}
