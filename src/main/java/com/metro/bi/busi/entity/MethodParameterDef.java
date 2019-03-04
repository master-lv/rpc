package com.metro.bi.busi.entity;

public class MethodParameterDef {


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFormalParamType() {
        return formalParamType;
    }

    public void setFormalParamType(String formalParamType) {
        this.formalParamType = formalParamType;
    }

    public String getFormalParamSubclass() {
        return formalParamSubclass;
    }

    public void setFormalParamSubclass(String formalParamSubclass) {
        this.formalParamSubclass = formalParamSubclass;
    }

    public String getFormalParamName() {
        return formalParamName;
    }

    public void setFormalParamName(String formalParamName) {
        this.formalParamName = formalParamName;
    }

    public int getFormalParamLength() {
        return formalParamLength;
    }

    public void setFormalParamLength(int formalParamLength) {
        this.formalParamLength = formalParamLength;
    }

    public int getFormalParamPrecision() {
        return formalParamPrecision;
    }

    public void setFormalParamPrecision(int formalParamPrecision) {
        this.formalParamPrecision = formalParamPrecision;
    }

    public String getAlgoCode() {
        return algoCode;
    }

    public void setAlgoCode(String algoCode) {
        this.algoCode = algoCode;
    }

    public String getMethodCode() {
        return methodCode;
    }

    public void setMethodCode(String methodCode) {
        this.methodCode = methodCode;
    }

    public String getFormalParamAttr() {
        return formalParamAttr;
    }

    public void setFormalParamAttr(String formalParamAttr) {
        this.formalParamAttr = formalParamAttr;
    }

    public String getFormalParamParent() {
        return formalParamParent;
    }

    public void setFormalParamParent(String formalParamParent) {
        this.formalParamParent = formalParamParent;
    }

    public String getParamStyle() {
        return paramStyle;
    }

    public void setParamStyle(String paramStyle) {
        this.paramStyle = paramStyle;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    private int id;
    private String formalParamType;
    private String formalParamSubclass;
    private String formalParamName;
    private int formalParamLength;
    private int formalParamPrecision;
    private String algoCode;
    private String methodCode;
    private String formalParamAttr;
    private String formalParamParent;
    private String paramStyle;
    private String descrip;

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    private int displayOrder;

    public int getPrimitiveTypeFlag() {
        return primitiveTypeFlag;
    }

    public void setPrimitiveTypeFlag(int primitiveTypeFlag) {
        this.primitiveTypeFlag = primitiveTypeFlag;
    }

    private int primitiveTypeFlag;

}
