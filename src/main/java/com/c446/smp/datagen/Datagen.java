package com.c446.smp.datagen;

import javax.annotation.Nullable;

public class Datagen {
    public static void main(String[] args) {
        // YOUR TASK HERE
        // EXAMPLE TASK :
        System.out.println(createAttributeConfig("insight_suppression_ratio", 3, 10, 0, "The insight suppression rate. It's value is calced by dividing it by 10 (if applicable)"));

        System.out.println(createConfigurableItemAttribute("focus_charm_1", "focus", 2,-1000, 1000));
    }

    public static String createAttributeConfig(String name, float base, float min, float max, @Nullable String description) {
        /**
         * @Param name : the attribute name, written in snake_casing
         * @Param base : the default value of the attribute.
         * @Param min : self-explanatory
         * @Param max : self-explanatory
         * */
        StringBuilder builder = new StringBuilder();
        builder
                .append("public static final ForgeConfigSpec.DoubleValue ")
                .append(name.toUpperCase())
                .append("\n")
                .append("// PUT FOLLOWING INTO STATIC METHOD : \n")
                .append(name.toUpperCase())
                .append(" = SERVER_BUILDER\n    .comment(\"")
        ;
        if ((description != null)) {
            builder.append(description);
        }
        builder
                .append("\")\n    .defineInRange(\"")
                .append(name.toLowerCase())
                .append("_config_path,")
                .append(base)
                .append("\",")
                .append(min)
                .append(",")
                .append(max)
                .append(");\n")
                .append("//END");
        return builder.toString();
    }

    public static String createAttributeConfig(String name, float base, @Nullable String description) {
        /**
         * Do note : in this function, min will be equal to $$-2^{(32)}$$, and max to $$2^{(32)}$$.
         * @Param name : the attribute name, written in snake_casing
         * @Param base : the default value of the attribute.
         * @Param description : the builder description
         * */
        StringBuilder builder = new StringBuilder();
        builder
                .append("public static final ForgeConfigSpec.DoubleValue ")
                .append(name.toUpperCase())
                .append("\n")
                .append("// PUT FOLLOWING INTO STATIC METHOD : \n")
                .append(name.toUpperCase())
                .append(" = SERVER_BUILDER\n    .comment(");
        if ((description != null)) {
            builder.append(description);
        }
        builder
                .append(")\n    .defineInRange(")
                .append(name.toLowerCase())
                .append("_config_path,")
                .append(base)
                .append(",")
                .append(Integer.MIN_VALUE)
                .append(",")
                .append(Integer.MAX_VALUE)
                .append(");\n")
                .append("//END");
        return builder.toString();
    }

    public static String createAttributeConfig(String name, float base) {
        /**
         * Do note : in this function, min will be equal to $$-2^{(32)}$$, and max to $$2^{(32)}$$.
         * @Param name : the attribute name, written in snake_casing
         * @Param base : the default value of the attribute.
         * @Param description : the builder description
         * */
        StringBuilder builder = new StringBuilder();
        builder
                .append("public static final ForgeConfigSpec.DoubleValue ")
                .append(name.toUpperCase())
                .append("\n")
                .append("// PUT FOLLOWING INTO STATIC METHOD : \n")
                .append(name.toUpperCase())
                .append(" = SERVER_BUILDER\n    .comment(")
                .append("NO DESCRIPTION AVAILABLE")
                .append(")\n    .defineInRange(")
                .append(name.toLowerCase())
                .append("_config_path,")
                .append(base)
                .append(",")
                .append(Integer.MIN_VALUE)
                .append(",")
                .append(Integer.MAX_VALUE)
                .append(");\n")
                .append("//END");
        return builder.toString();
    }


    public static String createConfigurableItemAttribute(String itemName, String attrName, float base, float min, float max) {
        /**
         * WHAT MUST BE DONE :
         *      - CREATE CONFIGS
         *      -
         * */
        StringBuilder fieldBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();
        String name = (itemName + "_config_" + attrName).toLowerCase();

        fieldBuilder
                .append("PUT IN SERVER CONFIG : \n")
                .append("public static final ForgeConfigSpec.DoubleValue ")
                .append(name.toUpperCase())
                .append("\n");
        valueBuilder
                .append(name.toUpperCase())
                .append(".comment(")
                .append("\"")
                .append("ITEM ")
                .append(itemName)
                .append(" ATTRIBUTE CONFIG FOR ")
                .append(attrName)
                .append("\"")
                .append(")")
                .append(".defineInRange(")
                .append("\"")
                .append(name)
                .append("_config_path")
                .append("\"")
                .append(",")
                .append(base)
                .append(",")
                .append(min)
                .append(",")
                .append(max)
                .append(");\n");
        return (fieldBuilder.toString() + valueBuilder.toString());
    }
}
