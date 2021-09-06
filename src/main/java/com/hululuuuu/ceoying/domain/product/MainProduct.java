package com.hululuuuu.ceoying.domain.product;

public enum MainProduct {
    TOOTH_PASTE_200("치약200g"),
    TOOTH_PASTE_250("치약250g"),
    TOOTH_PASTE_50("치약50g"),
    GINGER_TEA_2_IN_1("진만당2in1"),
    GINGER_TEA_4_IN_1("진만당4in1");

    private final String name;

    MainProduct(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
