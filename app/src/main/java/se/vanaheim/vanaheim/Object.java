package se.vanaheim.vanaheim;

public class Object {

    private String titel;
    private int idRow;
    private int rowInColumn;
    private int completed;
    private String editor;
    private double latitude;
    private double longitude;
    private String comments;

    //*********INF***************
    private String kmNummer;
    private String sparvidd;
    private String ralsforhojning;
    private String slipersavstand;
    private String sparavstand;
    private String friaRummet;
    //*******************************

    //*********ENE***************
    private String stolpnummer;
    private String objektForENE;
    private String hojdAvKontakttrad;
    private String avvikelseISidled;
    private String hojdAvUtliggarror;
    private String upphojdAvTillsatsror;
    //*******************************

    private int prmType;
    //*********PRM-ljudmätning***************
    private String plats;
    private String objektForPRMLjudmatning;
    private String arvarde;
    private String borvarde;
    private String medelvarde;
    private String avvikelse;
    private String anmarkning;
    //*******************************

    //*********PRM-ljusmätning***************
    private String projektNamnValue;
    private String textValue;
    private String widthValue;
    private String firstValue;
    private String secondValue;
    private String thirdValue;
    private String fourthValue;
    private String fifthValue;
    private String sixthValue;
    private String seventhValue;
    private String eightValue;
    private String ninthValue;
    private String tenthValue;
    private String eleventhValue;
    private String twelfthValue;
    private String thirteenthValue;
    private String fourteenthValue;
    private String startValue;
    //*******************************

    //****** Vaxlar och Spar för pdf:en********
    private String vaxlarComments;
    private String sparComments;
    private String overallComments;

    public Object() {
    }

    //**************************************************************************
    //************ General variables for all objects ***************************
    //**************************************************************************
    public int getRowInColumn() {
        return rowInColumn;
    }

    public void setRowInColumn(int rowInColumn) {
        this.rowInColumn = rowInColumn;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setIdRow(int id) {
        this.idRow = id;
    }

    public int getIdRow() {
        return this.idRow;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getLatitudeAsString() {
        String lang = Double.toString(latitude);
        return lang;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getLongitudeAsString() {
        String lon = Double.toString(longitude);
        return lon;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getCompleted() {
        return this.completed;
    }

    //****************************************************
    //************ INF objects ***************************
    //****************************************************

    public void setKmNummer(String kmNummer) {
        this.kmNummer = kmNummer;
    }


    public String getKmNummer() {

        if (this.kmNummer == null)
            this.kmNummer = "";
        return kmNummer;
    }

    public void setSparvidd(String sparvidd) {
        this.sparvidd = sparvidd;
    }

    public String getSparvidd() {
        if (this.sparvidd == null)
            this.sparvidd = "";
        return sparvidd;
    }

    public void setRalsforhojning(String ralsforhojning) {
        this.ralsforhojning = ralsforhojning;
    }

    public String getRalsforhojning() {
        if (this.ralsforhojning == null)
            this.ralsforhojning = "";
        return ralsforhojning;
    }

    public void setSlipersavstand(String slipersavstand) {
        this.slipersavstand = slipersavstand;
    }

    public String getSlipersavstand() {
        if (this.slipersavstand == null)
            this.slipersavstand = "";
        return slipersavstand;
    }

    public void setSparavstand(String sparavstand) {
        this.sparavstand = sparavstand;
    }

    public String getSparavstand() {
        if (this.sparavstand == null)
            this.sparavstand = "";
        return sparavstand;
    }

    public void setFriaRummet(String friaRummet) {
        this.friaRummet = friaRummet;
    }

    public String getFriaRummet() {
        if (this.friaRummet == null)
            this.friaRummet = "";
        return friaRummet;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        if (this.comments == null)
            this.comments = "";
        return comments;
    }

    //****************************************************
    //************ ENE objects ***************************
    //****************************************************

    public void setStolpnummer(String stolpnummer) {
        this.stolpnummer = stolpnummer;
    }

    public String getStolpnummer() {
        if (this.stolpnummer == null)
            this.stolpnummer = "";
        return stolpnummer;
    }

    public void setObjektForENE(String objektForENE) {
        this.objektForENE = objektForENE;
    }

    public String getObjektForENE() {
        if (this.objektForENE == null)
            this.objektForENE = "";
        return objektForENE;
    }

    public void setHojdAvKontakttrad(String hojdAvKontakttrad) {
        this.hojdAvKontakttrad = hojdAvKontakttrad;
    }

    public String getHojdAvKontakttrad() {
        if (this.hojdAvKontakttrad == null)
            this.hojdAvKontakttrad = "";
        return hojdAvKontakttrad;
    }

    public void setAvvikelseISidled(String avvikelseISidled) {
        this.avvikelseISidled = avvikelseISidled;
    }

    public String getAvvikelseISidled() {
        if (this.avvikelseISidled == null)
            this.avvikelseISidled = "";
        return avvikelseISidled;
    }

    public void setHojdAvUtliggarror(String hojdAvUtliggarror) {
        this.hojdAvUtliggarror = hojdAvUtliggarror;
    }

    public String getHojdAvUtliggarror() {
        if (this.hojdAvUtliggarror == null)
            this.hojdAvUtliggarror = "";
        return hojdAvUtliggarror;
    }

    public void setUpphojdAvTillsatsror(String upphojdAvTillsatsror) {
        this.upphojdAvTillsatsror = upphojdAvTillsatsror;
    }

    public String getUpphojdAvTillsatsror() {
        if (this.upphojdAvTillsatsror == null)
            this.upphojdAvTillsatsror = "";
        return upphojdAvTillsatsror;
    }

    //****************************************************
    //************ PRM Ljudmätning ***************************
    //****************************************************
    public int getPrmType() {
        return prmType;
    }

    public void setPrmType(int prmType) {
        this.prmType = prmType;
    }

    public void setPlats(String plats) {
        this.plats = plats;
    }

    public String getPlats() {
        if (this.plats == null)
            this.plats = "";
        return this.plats;
    }

    public void setObjektForPRMLjudmatning(String objektForPRMLjudmatning) {
        this.objektForPRMLjudmatning = objektForPRMLjudmatning;
    }

    public String getObjektForPRMLjudmatning() {
        if (this.objektForPRMLjudmatning == null)
            this.objektForPRMLjudmatning = "";
        return objektForPRMLjudmatning;
    }

    public void setArvarde(String arvarde) {
        this.arvarde = arvarde;
    }

    public String getArvarde() {
        if (this.arvarde == null)
            this.arvarde = "";
        return this.arvarde;
    }

    public void setBorvarde(String borvarde) {
        this.borvarde = borvarde;
    }

    public String getBorvarde() {
        if (this.borvarde == null)
            this.borvarde = "";
        return this.borvarde;
    }

    public void setMedelvarde(String medelvarde) {
        this.medelvarde = medelvarde;
    }

    public String getMedelvarde() {
        if (this.medelvarde == null)
            this.medelvarde = "";
        return this.medelvarde;
    }

    public void setAvvikelse(String avvikelse) {
        this.avvikelse = avvikelse;
    }

    public String getAvvikelse() {
        if (this.avvikelse == null)
            this.avvikelse = "";
        return this.avvikelse;
    }

    public void setAnmarkning(String anmarkning) {
        this.anmarkning = anmarkning;
    }

    public String getAnmarkning() {
        if (this.anmarkning == null)
            this.anmarkning = "";
        return this.anmarkning;
    }

    //****************************************************
    //************ PRM Ljusmätning ***************************
    //****************************************************
    public String getProjektNamnValue() {
        return projektNamnValue;
    }

    public void setProjektNamnValue(String projektNamnValue) {
        this.projektNamnValue = projektNamnValue;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public String getWidthValue() {
        return widthValue;
    }

    public void setWidthValue(String widthValue) {
        this.widthValue = widthValue;
    }

    public String getFirstValue() {
        return firstValue;
    }

    public void setFirstValue(String firstValue) {
        this.firstValue = firstValue;
    }

    public String getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(String secondValue) {
        this.secondValue = secondValue;
    }

    public String getThirdValue() {
        return thirdValue;
    }

    public void setThirdValue(String thirdValue) {
        this.thirdValue = thirdValue;
    }

    public String getFourthValue() {
        return fourthValue;
    }

    public void setFourthValue(String fourthValue) {
        this.fourthValue = fourthValue;
    }

    public String getFifthValue() {
        return fifthValue;
    }

    public void setFifthValue(String fifthValue) {
        this.fifthValue = fifthValue;
    }

    public String getSixthValue() {
        return sixthValue;
    }

    public void setSixthValue(String sixthValue) {
        this.sixthValue = sixthValue;
    }

    public String getSeventhValue() {
        return seventhValue;
    }

    public void setSeventhValue(String seventhValue) {
        this.seventhValue = seventhValue;
    }

    public String getEightValue() {
        return eightValue;
    }

    public void setEightValue(String eightValue) {
        this.eightValue = eightValue;
    }

    public String getNinthValue() {
        return ninthValue;
    }

    public void setNinthValue(String ninthValue) {
        this.ninthValue = ninthValue;
    }

    public String getTenthValue() {
        return tenthValue;
    }

    public void setTenthValue(String tenthValue) {
        this.tenthValue = tenthValue;
    }

    public String getEleventhValue() {
        return eleventhValue;
    }

    public void setEleventhValue(String eleventhValue) {
        this.eleventhValue = eleventhValue;
    }

    public String getTwelfthValue() {
        return twelfthValue;
    }

    public void setTwelfthValue(String twelfthValue) {
        this.twelfthValue = twelfthValue;
    }

    public String getThirteenthValue() {
        return thirteenthValue;
    }

    public void setThirteenthValue(String thirteenthValue) {
        this.thirteenthValue = thirteenthValue;
    }

    public String getFourteenthValue() {
        return fourteenthValue;
    }

    public void setFourteenthValue(String fourteenthValue) {
        this.fourteenthValue = fourteenthValue;
    }

    public String getStartValue() {
        return startValue;
    }

    public void setStartValue(String startValue) {
        this.startValue = startValue;
    }

    //****************** Vaxlar och Spar ************************
    public String getVaxlarComments() {
        if(this.vaxlarComments == null)
            this.vaxlarComments = "";
        return vaxlarComments;
    }

    public void setVaxlarComments(String vaxlarComments) {
        this.vaxlarComments = vaxlarComments;
    }

    public String getSparComments() {
        if(this.sparComments == null)
            this.sparComments = "";
        return sparComments;
    }

    public void setSparComments(String sparComments) {
        this.sparComments = sparComments;
    }

    public String getOverallComments() {
        if(this.overallComments == null)
            this.overallComments = "";
        return overallComments;
    }

    public void setOverallComments(String overallComments) {
        this.overallComments = overallComments;
    }
}
