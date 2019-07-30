package se.vanaheim.vanaheim;

public class PropertyListObjects {

    private boolean isEmpty;
    private int rowID;
    private String kapITSD;
    private String titel;

    //************ 4.2.1.1 Parkeringsmöjligheter för funktionshindrade *************
    private String parkeringsmojligheterForFunktionshindrade;
    private String parkeringsmojligheterForFunktionshindradeExtraObjects;
    private String placeringAvParkeringForFunktionshindrade;
    private String placeringAvParkeringForFunktionshindradeExtraObjects;

    //************ 4.2.1.2 Hinderfri gångväg*************
    private String forekomstAvHinderfriGangvag;
    private String forekomstAvHinderfriGangvagExtraObjects;
    private String langdenPaHindergriaGangvagar;
    private String langdenPaHindergriaGangvagarExtraObjects;
    private String reflekterandeEgenskaper;
    private String reflekterandeEgenskaperExtraObjects;

    //************ 4.2.1.2.1 Horisontell förflyttning************
    private String hinderfriGangvagsbredd;
    private String hinderfriGangvagsbreddExtraObjects;
    private String trosklarPaHinderfriGangvag;
    private String trosklarPaHinderfriGangvagExtraObjects;

    //************ 4.2.1.2.2 Vertikal förflyttning************
    private String trappstegsfriVag;
    private String trappstegsfriVagExtraObjects;
    private String breddPaTrappor;
    private String breddPaTrapporExtraObjects;
    private String visuelMarkeringPaForstaOchSistaSteget;
    private String visuelMarkeringPaForstaOchSistaStegetExtraObjects;
    private String taktilVarningForeForstaUppgaendeTrappsteg;
    private String taktilVarningForeForstaUppgaendeTrappstegExtraObjects;
    private String ramperForPersonerMedFunktionsnedsattningar;
    private String ramperForPersonerMedFunktionsnedsattningarExtraObjects;
    private String ledstangerPaBadaSidorOchTvaNivaer;
    private String ledstangerPaBadaSidorOchTvaNivaerExtraObjects;
    private String hissar;
    private String hissarExtraObjects;
    private String rulltrapporOchRullramper;
    private String rulltrapporOchRullramperExtraObjects;
    private String plankorsningar;
    private String plankorsningarExtraObjects;

    //************ 4.2.1.2.3 Gångvägsmarkering************
    private String tydligMarkering;
    private String tydligMarkeringExtraObjects;
    private String tillhandahållandeAvInformationTillSynskadade;
    private String tillhandahållandeAvInformationTillSynskadadeExtraObjects;
    private String fjarrstyrdaLjudanordningarEllerTeleapplikationer;
    private String fjarrstyrdaLjudanordningarEllerTeleapplikationerExtraObjects;
    private String taktilInformationPaLedstangerEllerVaggar;
    private String taktilInformationPaLedstangerEllerVaggarExtraObjects;

    //************ 4.2.1.4 Golvytor************
    private String halksakerhet;
    private String halksakerhetExtraObjects;
    private String ojamnheterSomOverstiger;
    private String ojamnheterSomOverstigerExtraObjects;

    //************ 4.2.1.5 Markering av genomskinliga hinder************
    private String glasdorrarEllerGenomskinligaVaggarLangsGangvagar;
    private String glasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraObjects;

    //************ 4.2.1.6 Toaletter och skötplatser************
    private String toalettutrymmeAnpassatForRullstolsburnaPersoner;
    private String toalettutrymmeAnpassatForRullstolsburnaPersonerExtraObjects;
    private String skotplatserTillgangligaForBadeKonen;
    private String skotplatserTillgangligaForBadeKonenExtraObjects;

    //************ 4.2.1.7 Inredning och fristående enheter************
    private String kontrastMotBakgrundOchAvrundandeKanter;
    private String kontrastMotBakgrundOchAvrundandeKanterExtraObjects;
    private String placeringAvInredningOchEnheter;
    private String placeringAvInredningOchEnheterExtraObjects;
    private String sittmojligheterOchPlatsForEnRullstolsbundenPerson;
    private String sittmojligheterOchPlatsForEnRullstolsbundenPersonExtraObjects;
    private String vaderskyddatOmrade;
    private String vaderskyddatOmradeExtraObjects;

    //************4.2.1.9 Belysning************
    private String belysningPaStationensExternaOmraden;
    private String belysningPaStationensExternaOmradenExtraObjects;
    private String belysningLangsHinderfriaGangvagar;
    private String belysningLangsHinderfriaGangvagarExtraObjects;
    private String belysningPaPlattformar;
    private String belysningPaPlattformarExtraObjects;
    private String nodbelysning;
    private String nodbelysningExtraObjects;

    //************4.2.1.10 Visuell information************
    private String skyltarAvstand;
    private String skyltarAvstandExtraObjects;
    private String pictogram;
    private String pictogramExtraObjects;
    private String kontrast;
    private String kontrastExtraObjects;
    private String enhetlig;
    private String enhetligExtraObjects;
    private String synligIAllaBelysningsforhallanden;
    private String synligIAllaBelysningsforhallandenExtraObjects;
    private String skyltarEnligtISO;
    private String skyltarEnligtISOExtraObjects;

    //************4.2.1.11 Talad information Sidoplattform***********
    private String stipaNiva;
    private String stipaNivaExtraObjects;

    //************4.2.1.12 Plattformsbredd och plattformskant************
    private String forekomstAvRiskomrade;
    private String forekomstAvRiskomradeExtraObjects;
    private String plattformsMinstaBredd;
    private  String plattformsMinstaBreddExtraObjects;
    private String avstandMellanLitetHinder;
    private String avstandMellanLitetHinderExtraObjects;
    private String avstandMellanStortHinder;
    private String avstandMellanStortHinderExtraObjects;
    private String markeringAvRiskomradetsGrans;
    private String markeringAvRiskomradetsGransExtraObjects;
    private String breddenPaVarningslinjeOchHalksakerhetOchFarg;
    private String breddenPaVarningslinjeOchHalksakerhetOchFargExtraObjects;
    private String materialPaPlattformskanten;
    private String materialPaPlattformskantenExtraObjects;

    //************4.2.1.13 Plattformens slut************
    private String markeringAvPlattformensSlut;
    private String markeringAvPlattformensSlutExtraObjects;

    //************4.2.1.15 Spårkorsning för passagerare påväg till plattformar************
    private String anvandsSomEnDelAvTrappstegfriGangvag;
    private String anvandsSomEnDelAvTrappstegfriGangvagExtraObjects;
    private String breddPaGangvagg;
    private String breddPaGangvaggExtraObjects;
    private String lutning;
    private String lutningExtraObjects;
    private String friPassageForMinstaHjuletPaEnRullstol;
    private String friPassageForMinstaHjuletPaEnRullstolExtraObjects;
    private String friPassageOmSakerhetschikanerForekommer;
    private String friPassageOmSakerhetschikanerForekommerExtraObjects;
    private String markeringAvGangbaneytan;
    private String markeringAvGangbaneytanExtraObjects;
    private String sakerPassage;
    private String sakerPassageExtraObjects;

    public PropertyListObjects() {

        isEmpty = true;
        //************ 4.2.1.1 Parkeringsmöjligheter för funktionshindrade *************
        parkeringsmojligheterForFunktionshindrade = "";
        parkeringsmojligheterForFunktionshindradeExtraObjects = "";
        placeringAvParkeringForFunktionshindrade = "";
        placeringAvParkeringForFunktionshindradeExtraObjects = "";

        //************ 4.2.1.2 Hinderfri gångväg*************
        forekomstAvHinderfriGangvag = "";
        forekomstAvHinderfriGangvagExtraObjects = "";
        langdenPaHindergriaGangvagar = "";
        langdenPaHindergriaGangvagarExtraObjects = "";
        reflekterandeEgenskaper = "";
        reflekterandeEgenskaperExtraObjects = "";

        //************ 4.2.1.2.1 Horisontell förflyttning************
        hinderfriGangvagsbredd = "";
        hinderfriGangvagsbreddExtraObjects = "";
        trosklarPaHinderfriGangvag = "";
        trosklarPaHinderfriGangvagExtraObjects = "";

        //************ 4.2.1.2.2 Vertikal förflyttning************
        trappstegsfriVag = "";
        trappstegsfriVagExtraObjects = "";
        breddPaTrappor = "";
        breddPaTrapporExtraObjects = "";
        visuelMarkeringPaForstaOchSistaSteget = "";
        visuelMarkeringPaForstaOchSistaStegetExtraObjects = "";
        taktilVarningForeForstaUppgaendeTrappsteg = "";
        taktilVarningForeForstaUppgaendeTrappstegExtraObjects = "";
        ramperForPersonerMedFunktionsnedsattningar = "";
        ramperForPersonerMedFunktionsnedsattningarExtraObjects = "";
        ledstangerPaBadaSidorOchTvaNivaer = "";
        ledstangerPaBadaSidorOchTvaNivaerExtraObjects = "";
        hissar = "";
        hissarExtraObjects = "";
        rulltrapporOchRullramper = "";
        rulltrapporOchRullramperExtraObjects = "";
        plankorsningar = "";
        plankorsningarExtraObjects = "";

        //************ 4.2.1.2.3 Gångvägsmarkering************
        tydligMarkering = "";
        tydligMarkeringExtraObjects = "";
        tillhandahållandeAvInformationTillSynskadade = "";
        tillhandahållandeAvInformationTillSynskadadeExtraObjects = "";
        fjarrstyrdaLjudanordningarEllerTeleapplikationer = "";
        fjarrstyrdaLjudanordningarEllerTeleapplikationerExtraObjects = "";
        taktilInformationPaLedstangerEllerVaggar = "";
        taktilInformationPaLedstangerEllerVaggarExtraObjects = "";

        //************ 4.2.1.4 Golvytor************
        halksakerhet = "";
        halksakerhetExtraObjects = "";
        ojamnheterSomOverstiger = "";
        ojamnheterSomOverstigerExtraObjects = "";

        //************ 4.2.1.5 Markering av genomskinliga hinder************
        glasdorrarEllerGenomskinligaVaggarLangsGangvagar = "";
        glasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraObjects = "";

        //************ 4.2.1.6 Toaletter och skötplatser************
        toalettutrymmeAnpassatForRullstolsburnaPersoner = "";
        toalettutrymmeAnpassatForRullstolsburnaPersonerExtraObjects = "";
        skotplatserTillgangligaForBadeKonen = "";
        skotplatserTillgangligaForBadeKonenExtraObjects = "";

        //************ 4.2.1.7 Inredning och fristående enheter************
        kontrastMotBakgrundOchAvrundandeKanter = "";
        kontrastMotBakgrundOchAvrundandeKanterExtraObjects = "";
        placeringAvInredningOchEnheter = "";
        placeringAvInredningOchEnheterExtraObjects = "";
        sittmojligheterOchPlatsForEnRullstolsbundenPerson = "";
        sittmojligheterOchPlatsForEnRullstolsbundenPersonExtraObjects = "";
        vaderskyddatOmrade = "";
        vaderskyddatOmradeExtraObjects = "";

        //************4.2.1.9 Belysning************
        belysningPaStationensExternaOmraden = "";
        belysningPaStationensExternaOmradenExtraObjects = "";
        belysningLangsHinderfriaGangvagar = "";
        belysningLangsHinderfriaGangvagarExtraObjects = "";
        belysningPaPlattformar = "";
        belysningPaPlattformarExtraObjects = "";
        nodbelysning = "";
        nodbelysningExtraObjects = "";

        //************4.2.1.10 Visuell information************
        skyltarAvstand = "";
        skyltarAvstandExtraObjects = "";
        pictogram = "";
        pictogramExtraObjects = "";
        kontrast = "";
        kontrastExtraObjects = "";
        enhetlig = "";
        enhetligExtraObjects = "";
        synligIAllaBelysningsforhallanden = "";
        synligIAllaBelysningsforhallandenExtraObjects = "";
        skyltarEnligtISO = "";
        skyltarEnligtISOExtraObjects = "";

        //************4.2.1.11 Talad information Sidoplattform***********
        stipaNiva = "";
        stipaNivaExtraObjects = "";

        //************4.2.1.12 Plattformsbredd och plattformskant************
        forekomstAvRiskomrade = "";
        forekomstAvRiskomradeExtraObjects = "";
        plattformsMinstaBredd = "";
        plattformsMinstaBreddExtraObjects = "";
        avstandMellanLitetHinder = "";
        avstandMellanLitetHinderExtraObjects = "";
        avstandMellanStortHinder = "";
        avstandMellanStortHinderExtraObjects = "";
        markeringAvRiskomradetsGrans = "";
        markeringAvRiskomradetsGransExtraObjects = "";
        breddenPaVarningslinjeOchHalksakerhetOchFarg = "";
        breddenPaVarningslinjeOchHalksakerhetOchFargExtraObjects = "";
        materialPaPlattformskanten = "";
        materialPaPlattformskantenExtraObjects = "";

        //************4.2.1.13 Plattformens slut************
        markeringAvPlattformensSlut = "";
        markeringAvPlattformensSlutExtraObjects = "";

        //************4.2.1.15 Spårkorsning för passagerare påväg till plattformar************
        anvandsSomEnDelAvTrappstegfriGangvag = "";
        anvandsSomEnDelAvTrappstegfriGangvagExtraObjects = "";
        breddPaGangvagg = "";
        breddPaGangvaggExtraObjects = "";
        lutning = "";
        lutningExtraObjects = "";
        friPassageForMinstaHjuletPaEnRullstol = "";
        friPassageForMinstaHjuletPaEnRullstolExtraObjects = "";
        friPassageOmSakerhetschikanerForekommer = "";
        friPassageOmSakerhetschikanerForekommerExtraObjects = "";
        markeringAvGangbaneytan = "";
        markeringAvGangbaneytanExtraObjects = "";
        sakerPassage = "";
        sakerPassageExtraObjects = "";
    }

    public boolean getIsEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public int getRowID() {
        return rowID;
    }

    public void setRowID(int rowID) {
        this.rowID = rowID;
    }

    public String getKapITSD() {
        return kapITSD;
    }

    public void setKapITSD(String kapITSD) {
        this.kapITSD = kapITSD;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getParkeringsmojligheterForFunktionshindrade() {
        return parkeringsmojligheterForFunktionshindrade;
    }

    public void setParkeringsmojligheterForFunktionshindrade(String parkeringsmojligheterForFunktionshindrade) {
        this.parkeringsmojligheterForFunktionshindrade = parkeringsmojligheterForFunktionshindrade;
    }

    public String getParkeringsmojligheterForFunktionshindradeExtraObjects() {
        return parkeringsmojligheterForFunktionshindradeExtraObjects;
    }

    public void setParkeringsmojligheterForFunktionshindradeExtraObjects(String parkeringsmojligheterForFunktionshindradeExtraObjects) {
        this.parkeringsmojligheterForFunktionshindradeExtraObjects = parkeringsmojligheterForFunktionshindradeExtraObjects;
    }

    public String getPlaceringAvParkeringForFunktionshindrade() {
        return placeringAvParkeringForFunktionshindrade;
    }

    public void setPlaceringAvParkeringForFunktionshindrade(String placeringAvParkeringForFunktionshindrade) {
        this.placeringAvParkeringForFunktionshindrade = placeringAvParkeringForFunktionshindrade;
    }

    public String getPlaceringAvParkeringForFunktionshindradeExtraObjects() {
        return placeringAvParkeringForFunktionshindradeExtraObjects;
    }

    public void setPlaceringAvParkeringForFunktionshindradeExtraObjects(String placeringAvParkeringForFunktionshindradeExtraObjects) {
        this.placeringAvParkeringForFunktionshindradeExtraObjects = placeringAvParkeringForFunktionshindradeExtraObjects;
    }

    public String getForekomstAvHinderfriGangvag() {
        return forekomstAvHinderfriGangvag;
    }

    public void setForekomstAvHinderfriGangvag(String forekomstAvHinderfriGangvag) {
        this.forekomstAvHinderfriGangvag = forekomstAvHinderfriGangvag;
    }

    public String getForekomstAvHinderfriGangvagExtraObjects() {
        return forekomstAvHinderfriGangvagExtraObjects;
    }

    public void setForekomstAvHinderfriGangvagExtraObjects(String forekomstAvHinderfriGangvagExtraObjects) {
        this.forekomstAvHinderfriGangvagExtraObjects = forekomstAvHinderfriGangvagExtraObjects;
    }

    public String getLangdenPaHindergriaGangvagar() {
        return langdenPaHindergriaGangvagar;
    }

    public void setLangdenPaHindergriaGangvagar(String langdenPaHindergriaGangvagar) {
        this.langdenPaHindergriaGangvagar = langdenPaHindergriaGangvagar;
    }

    public String getLangdenPaHindergriaGangvagarExtraObjects() {
        return langdenPaHindergriaGangvagarExtraObjects;
    }

    public void setLangdenPaHindergriaGangvagarExtraObjects(String langdenPaHindergriaGangvagarExtraObjects) {
        this.langdenPaHindergriaGangvagarExtraObjects = langdenPaHindergriaGangvagarExtraObjects;
    }

    public String getReflekterandeEgenskaper() {
        return reflekterandeEgenskaper;
    }

    public void setReflekterandeEgenskaper(String reflekterandeEgenskaper) {
        this.reflekterandeEgenskaper = reflekterandeEgenskaper;
    }

    public String getReflekterandeEgenskaperExtraObjects() {
        return reflekterandeEgenskaperExtraObjects;
    }

    public void setReflekterandeEgenskaperExtraObjects(String reflekterandeEgenskaperExtraObjects) {
        this.reflekterandeEgenskaperExtraObjects = reflekterandeEgenskaperExtraObjects;
    }

    public String getHinderfriGangvagsbredd() {
        return hinderfriGangvagsbredd;
    }

    public void setHinderfriGangvagsbredd(String hinderfriGangvagsbredd) {
        this.hinderfriGangvagsbredd = hinderfriGangvagsbredd;
    }

    public String getHinderfriGangvagsbreddExtraObjects() {
        return hinderfriGangvagsbreddExtraObjects;
    }

    public void setHinderfriGangvagsbreddExtraObjects(String hinderfriGangvagsbreddExtraObjects) {
        this.hinderfriGangvagsbreddExtraObjects = hinderfriGangvagsbreddExtraObjects;
    }

    public String getTrosklarPaHinderfriGangvag() {
        return trosklarPaHinderfriGangvag;
    }

    public void setTrosklarPaHinderfriGangvag(String trosklarPaHinderfriGangvag) {
        this.trosklarPaHinderfriGangvag = trosklarPaHinderfriGangvag;
    }

    public String getTrosklarPaHinderfriGangvagExtraObjects() {
        return trosklarPaHinderfriGangvagExtraObjects;
    }

    public void setTrosklarPaHinderfriGangvagExtraObjects(String trosklarPaHinderfriGangvagExtraObjects) {
        this.trosklarPaHinderfriGangvagExtraObjects = trosklarPaHinderfriGangvagExtraObjects;
    }

    public String getTrappstegsfriVag() {
        return trappstegsfriVag;
    }

    public void setTrappstegsfriVag(String trappstegsfriVag) {
        this.trappstegsfriVag = trappstegsfriVag;
    }

    public String getTrappstegsfriVagExtraObjects() {
        return trappstegsfriVagExtraObjects;
    }

    public void setTrappstegsfriVagExtraObjects(String trappstegsfriVagExtraObjects) {
        this.trappstegsfriVagExtraObjects = trappstegsfriVagExtraObjects;
    }

    public String getBreddPaTrappor() {
        return breddPaTrappor;
    }

    public void setBreddPaTrappor(String breddPaTrappor) {
        this.breddPaTrappor = breddPaTrappor;
    }

    public String getBreddPaTrapporExtraObjects() {
        return breddPaTrapporExtraObjects;
    }

    public void setBreddPaTrapporExtraObjects(String breddPaTrapporExtraObjects) {
        this.breddPaTrapporExtraObjects = breddPaTrapporExtraObjects;
    }

    public String getVisuelMarkeringPaForstaOchSistaSteget() {
        return visuelMarkeringPaForstaOchSistaSteget;
    }

    public void setVisuelMarkeringPaForstaOchSistaSteget(String visuelMarkeringPaForstaOchSistaSteget) {
        this.visuelMarkeringPaForstaOchSistaSteget = visuelMarkeringPaForstaOchSistaSteget;
    }

    public String getVisuelMarkeringPaForstaOchSistaStegetExtraObjects() {
        return visuelMarkeringPaForstaOchSistaStegetExtraObjects;
    }

    public void setVisuelMarkeringPaForstaOchSistaStegetExtraObjects(String visuelMarkeringPaForstaOchSistaStegetExtraObjects) {
        this.visuelMarkeringPaForstaOchSistaStegetExtraObjects = visuelMarkeringPaForstaOchSistaStegetExtraObjects;
    }

    public String getTaktilVarningForeForstaUppgaendeTrappsteg() {
        return taktilVarningForeForstaUppgaendeTrappsteg;
    }

    public void setTaktilVarningForeForstaUppgaendeTrappsteg(String taktilVarningForeForstaUppgaendeTrappsteg) {
        this.taktilVarningForeForstaUppgaendeTrappsteg = taktilVarningForeForstaUppgaendeTrappsteg;
    }

    public String getTaktilVarningForeForstaUppgaendeTrappstegExtraObjects() {
        return taktilVarningForeForstaUppgaendeTrappstegExtraObjects;
    }

    public void setTaktilVarningForeForstaUppgaendeTrappstegExtraObjects(String taktilVarningForeForstaUppgaendeTrappstegExtraObjects) {
        this.taktilVarningForeForstaUppgaendeTrappstegExtraObjects = taktilVarningForeForstaUppgaendeTrappstegExtraObjects;
    }

    public String getRamperForPersonerMedFunktionsnedsattningar() {
        return ramperForPersonerMedFunktionsnedsattningar;
    }

    public void setRamperForPersonerMedFunktionsnedsattningar(String ramperForPersonerMedFunktionsnedsattningar) {
        this.ramperForPersonerMedFunktionsnedsattningar = ramperForPersonerMedFunktionsnedsattningar;
    }

    public String getRamperForPersonerMedFunktionsnedsattningarExtraObjects() {
        return ramperForPersonerMedFunktionsnedsattningarExtraObjects;
    }

    public void setRamperForPersonerMedFunktionsnedsattningarExtraObjects(String ramperForPersonerMedFunktionsnedsattningarExtraObjects) {
        this.ramperForPersonerMedFunktionsnedsattningarExtraObjects = ramperForPersonerMedFunktionsnedsattningarExtraObjects;
    }

    public String getLedstangerPaBadaSidorOchTvaNivaer() {
        return ledstangerPaBadaSidorOchTvaNivaer;
    }

    public void setLedstangerPaBadaSidorOchTvaNivaer(String ledstangerPaBadaSidorOchTvaNivaer) {
        this.ledstangerPaBadaSidorOchTvaNivaer = ledstangerPaBadaSidorOchTvaNivaer;
    }

    public String getLedstangerPaBadaSidorOchTvaNivaerExtraObjects() {
        return ledstangerPaBadaSidorOchTvaNivaerExtraObjects;
    }

    public void setLedstangerPaBadaSidorOchTvaNivaerExtraObjects(String ledstangerPaBadaSidorOchTvaNivaerExtraObjects) {
        this.ledstangerPaBadaSidorOchTvaNivaerExtraObjects = ledstangerPaBadaSidorOchTvaNivaerExtraObjects;
    }

    public String getHissar() {
        return hissar;
    }

    public void setHissar(String hissar) {
        this.hissar = hissar;
    }

    public String getHissarExtraObjects() {
        return hissarExtraObjects;
    }

    public void setHissarExtraObjects(String hissarExtraObjects) {
        this.hissarExtraObjects = hissarExtraObjects;
    }

    public String getRulltrapporOchRullramper() {
        return rulltrapporOchRullramper;
    }

    public void setRulltrapporOchRullramper(String rulltrapporOchRullramper) {
        this.rulltrapporOchRullramper = rulltrapporOchRullramper;
    }

    public String getRulltrapporOchRullramperExtraObjects() {
        return rulltrapporOchRullramperExtraObjects;
    }

    public void setRulltrapporOchRullramperExtraObjects(String rulltrapporOchRullramperExtraObjects) {
        this.rulltrapporOchRullramperExtraObjects = rulltrapporOchRullramperExtraObjects;
    }

    public String getPlankorsningar() {
        return plankorsningar;
    }

    public void setPlankorsningar(String plankorsningar) {
        this.plankorsningar = plankorsningar;
    }

    public String getPlankorsningarExtraObjects() {
        return plankorsningarExtraObjects;
    }

    public void setPlankorsningarExtraObjects(String plankorsningarExtraObjects) {
        this.plankorsningarExtraObjects = plankorsningarExtraObjects;
    }

    public String getTydligMarkering() {
        return tydligMarkering;
    }

    public void setTydligMarkering(String tydligMarkering) {
        this.tydligMarkering = tydligMarkering;
    }

    public String getTydligMarkeringExtraObjects() {
        return tydligMarkeringExtraObjects;
    }

    public void setTydligMarkeringExtraObjects(String tydligMarkeringExtraObjects) {
        this.tydligMarkeringExtraObjects = tydligMarkeringExtraObjects;
    }

    public String getTillhandahållandeAvInformationTillSynskadade() {
        return tillhandahållandeAvInformationTillSynskadade;
    }

    public void setTillhandahållandeAvInformationTillSynskadade(String tillhandahållandeAvInformationTillSynskadade) {
        this.tillhandahållandeAvInformationTillSynskadade = tillhandahållandeAvInformationTillSynskadade;
    }

    public String getTillhandahållandeAvInformationTillSynskadadeExtraObjects() {
        return tillhandahållandeAvInformationTillSynskadadeExtraObjects;
    }

    public void setTillhandahållandeAvInformationTillSynskadadeExtraObjects(String tillhandahållandeAvInformationTillSynskadadeExtraObjects) {
        this.tillhandahållandeAvInformationTillSynskadadeExtraObjects = tillhandahållandeAvInformationTillSynskadadeExtraObjects;
    }

    public String getFjarrstyrdaLjudanordningarEllerTeleapplikationer() {
        return fjarrstyrdaLjudanordningarEllerTeleapplikationer;
    }

    public void setFjarrstyrdaLjudanordningarEllerTeleapplikationer(String fjarrstyrdaLjudanordningarEllerTeleapplikationer) {
        this.fjarrstyrdaLjudanordningarEllerTeleapplikationer = fjarrstyrdaLjudanordningarEllerTeleapplikationer;
    }

    public String getFjarrstyrdaLjudanordningarEllerTeleapplikationerExtraObjects() {
        return fjarrstyrdaLjudanordningarEllerTeleapplikationerExtraObjects;
    }

    public void setFjarrstyrdaLjudanordningarEllerTeleapplikationerExtraObjects(String fjarrstyrdaLjudanordningarEllerTeleapplikationerExtraObjects) {
        this.fjarrstyrdaLjudanordningarEllerTeleapplikationerExtraObjects = fjarrstyrdaLjudanordningarEllerTeleapplikationerExtraObjects;
    }

    public String getTaktilInformationPaLedstangerEllerVaggar() {
        return taktilInformationPaLedstangerEllerVaggar;
    }

    public void setTaktilInformationPaLedstangerEllerVaggar(String taktilInformationPaLedstangerEllerVaggar) {
        this.taktilInformationPaLedstangerEllerVaggar = taktilInformationPaLedstangerEllerVaggar;
    }

    public String getTaktilInformationPaLedstangerEllerVaggarExtraObjects() {
        return taktilInformationPaLedstangerEllerVaggarExtraObjects;
    }

    public void setTaktilInformationPaLedstangerEllerVaggarExtraObjects(String taktilInformationPaLedstangerEllerVaggarExtraObjects) {
        this.taktilInformationPaLedstangerEllerVaggarExtraObjects = taktilInformationPaLedstangerEllerVaggarExtraObjects;
    }

    public String getHalksakerhet() {
        return halksakerhet;
    }

    public void setHalksakerhet(String halksakerhet) {
        this.halksakerhet = halksakerhet;
    }

    public String getHalksakerhetExtraObjects() {
        return halksakerhetExtraObjects;
    }

    public void setHalksakerhetExtraObjects(String halksakerhetExtraObjects) {
        this.halksakerhetExtraObjects = halksakerhetExtraObjects;
    }

    public String getOjamnheterSomOverstiger() {
        return ojamnheterSomOverstiger;
    }

    public void setOjamnheterSomOverstiger(String ojamnheterSomOverstiger) {
        this.ojamnheterSomOverstiger = ojamnheterSomOverstiger;
    }

    public String getOjamnheterSomOverstigerExtraObjects() {
        return ojamnheterSomOverstigerExtraObjects;
    }

    public void setOjamnheterSomOverstigerExtraObjects(String ojamnheterSomOverstigerExtraObjects) {
        this.ojamnheterSomOverstigerExtraObjects = ojamnheterSomOverstigerExtraObjects;
    }

    public String getGlasdorrarEllerGenomskinligaVaggarLangsGangvagar() {
        return glasdorrarEllerGenomskinligaVaggarLangsGangvagar;
    }

    public void setGlasdorrarEllerGenomskinligaVaggarLangsGangvagar(String glasdorrarEllerGenomskinligaVaggarLangsGangvagar) {
        this.glasdorrarEllerGenomskinligaVaggarLangsGangvagar = glasdorrarEllerGenomskinligaVaggarLangsGangvagar;
    }

    public String getGlasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraObjects() {
        return glasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraObjects;
    }

    public void setGlasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraObjects(String glasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraObjects) {
        this.glasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraObjects = glasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraObjects;
    }

    public String getToalettutrymmeAnpassatForRullstolsburnaPersoner() {
        return toalettutrymmeAnpassatForRullstolsburnaPersoner;
    }

    public void setToalettutrymmeAnpassatForRullstolsburnaPersoner(String toalettutrymmeAnpassatForRullstolsburnaPersoner) {
        this.toalettutrymmeAnpassatForRullstolsburnaPersoner = toalettutrymmeAnpassatForRullstolsburnaPersoner;
    }

    public String getToalettutrymmeAnpassatForRullstolsburnaPersonerExtraObjects() {
        return toalettutrymmeAnpassatForRullstolsburnaPersonerExtraObjects;
    }

    public void setToalettutrymmeAnpassatForRullstolsburnaPersonerExtraObjects(String toalettutrymmeAnpassatForRullstolsburnaPersonerExtraObjects) {
        this.toalettutrymmeAnpassatForRullstolsburnaPersonerExtraObjects = toalettutrymmeAnpassatForRullstolsburnaPersonerExtraObjects;
    }

    public String getSkotplatserTillgangligaForBadeKonen() {
        return skotplatserTillgangligaForBadeKonen;
    }

    public void setSkotplatserTillgangligaForBadeKonen(String skotplatserTillgangligaForBadeKonen) {
        this.skotplatserTillgangligaForBadeKonen = skotplatserTillgangligaForBadeKonen;
    }

    public String getSkotplatserTillgangligaForBadeKonenExtraObjects() {
        return skotplatserTillgangligaForBadeKonenExtraObjects;
    }

    public void setSkotplatserTillgangligaForBadeKonenExtraObjects(String skotplatserTillgangligaForBadeKonenExtraObjects) {
        this.skotplatserTillgangligaForBadeKonenExtraObjects = skotplatserTillgangligaForBadeKonenExtraObjects;
    }

    public String getKontrastMotBakgrundOchAvrundandeKanter() {
        return kontrastMotBakgrundOchAvrundandeKanter;
    }

    public void setKontrastMotBakgrundOchAvrundandeKanter(String kontrastMotBakgrundOchAvrundandeKanter) {
        this.kontrastMotBakgrundOchAvrundandeKanter = kontrastMotBakgrundOchAvrundandeKanter;
    }

    public String getKontrastMotBakgrundOchAvrundandeKanterExtraObjects() {
        return kontrastMotBakgrundOchAvrundandeKanterExtraObjects;
    }

    public void setKontrastMotBakgrundOchAvrundandeKanterExtraObjects(String kontrastMotBakgrundOchAvrundandeKanterExtraObjects) {
        this.kontrastMotBakgrundOchAvrundandeKanterExtraObjects = kontrastMotBakgrundOchAvrundandeKanterExtraObjects;
    }

    public String getPlaceringAvInredningOchEnheter() {
        return placeringAvInredningOchEnheter;
    }

    public void setPlaceringAvInredningOchEnheter(String placeringAvInredningOchEnheter) {
        this.placeringAvInredningOchEnheter = placeringAvInredningOchEnheter;
    }

    public String getPlaceringAvInredningOchEnheterExtraObjects() {
        return placeringAvInredningOchEnheterExtraObjects;
    }

    public void setPlaceringAvInredningOchEnheterExtraObjects(String placeringAvInredningOchEnheterExtraObjects) {
        this.placeringAvInredningOchEnheterExtraObjects = placeringAvInredningOchEnheterExtraObjects;
    }

    public String getSittmojligheterOchPlatsForEnRullstolsbundenPerson() {
        return sittmojligheterOchPlatsForEnRullstolsbundenPerson;
    }

    public void setSittmojligheterOchPlatsForEnRullstolsbundenPerson(String sittmojligheterOchPlatsForEnRullstolsbundenPerson) {
        this.sittmojligheterOchPlatsForEnRullstolsbundenPerson = sittmojligheterOchPlatsForEnRullstolsbundenPerson;
    }

    public String getSittmojligheterOchPlatsForEnRullstolsbundenPersonExtraObjects() {
        return sittmojligheterOchPlatsForEnRullstolsbundenPersonExtraObjects;
    }

    public void setSittmojligheterOchPlatsForEnRullstolsbundenPersonExtraObjects(String sittmojligheterOchPlatsForEnRullstolsbundenPersonExtraObjects) {
        this.sittmojligheterOchPlatsForEnRullstolsbundenPersonExtraObjects = sittmojligheterOchPlatsForEnRullstolsbundenPersonExtraObjects;
    }

    public String getVaderskyddatOmrade() {
        return vaderskyddatOmrade;
    }

    public void setVaderskyddatOmrade(String vaderskyddatOmrade) {
        this.vaderskyddatOmrade = vaderskyddatOmrade;
    }

    public String getVaderskyddatOmradeExtraObjects() {
        return vaderskyddatOmradeExtraObjects;
    }

    public void setVaderskyddatOmradeExtraObjects(String vaderskyddatOmradeExtraObjects) {
        this.vaderskyddatOmradeExtraObjects = vaderskyddatOmradeExtraObjects;
    }

    public String getBelysningPaStationensExternaOmraden() {
        return belysningPaStationensExternaOmraden;
    }

    public void setBelysningPaStationensExternaOmraden(String belysningPaStationensExternaOmraden) {
        this.belysningPaStationensExternaOmraden = belysningPaStationensExternaOmraden;
    }

    public String getBelysningPaStationensExternaOmradenExtraObjects() {
        return belysningPaStationensExternaOmradenExtraObjects;
    }

    public void setBelysningPaStationensExternaOmradenExtraObjects(String belysningPaStationensExternaOmradenExtraObjects) {
        this.belysningPaStationensExternaOmradenExtraObjects = belysningPaStationensExternaOmradenExtraObjects;
    }

    public String getBelysningLangsHinderfriaGangvagar() {
        return belysningLangsHinderfriaGangvagar;
    }

    public void setBelysningLangsHinderfriaGangvagar(String belysningLangsHinderfriaGangvagar) {
        this.belysningLangsHinderfriaGangvagar = belysningLangsHinderfriaGangvagar;
    }

    public String getBelysningLangsHinderfriaGangvagarExtraObjects() {
        return belysningLangsHinderfriaGangvagarExtraObjects;
    }

    public void setBelysningLangsHinderfriaGangvagarExtraObjects(String belysningLangsHinderfriaGangvagarExtraObjects) {
        this.belysningLangsHinderfriaGangvagarExtraObjects = belysningLangsHinderfriaGangvagarExtraObjects;
    }

    public String getBelysningPaPlattformar() {
        return belysningPaPlattformar;
    }

    public void setBelysningPaPlattformar(String belysningPaPlattformar) {
        this.belysningPaPlattformar = belysningPaPlattformar;
    }

    public String getBelysningPaPlattformarExtraObjects() {
        return belysningPaPlattformarExtraObjects;
    }

    public void setBelysningPaPlattformarExtraObjects(String belysningPaPlattformarExtraObjects) {
        this.belysningPaPlattformarExtraObjects = belysningPaPlattformarExtraObjects;
    }

    public String getNodbelysning() {
        return nodbelysning;
    }

    public void setNodbelysning(String nodbelysning) {
        this.nodbelysning = nodbelysning;
    }

    public String getNodbelysningExtraObjects() {
        return nodbelysningExtraObjects;
    }

    public void setNodbelysningExtraObjects(String nodbelysningExtraObjects) {
        this.nodbelysningExtraObjects = nodbelysningExtraObjects;
    }

    public String getSkyltarAvstand() {
        return skyltarAvstand;
    }

    public void setSkyltarAvstand(String skyltarAvstand) {
        this.skyltarAvstand = skyltarAvstand;
    }

    public String getSkyltarAvstandExtraObjects() {
        return skyltarAvstandExtraObjects;
    }

    public void setSkyltarAvstandExtraObjects(String skyltarAvstandExtraObjects) {
        this.skyltarAvstandExtraObjects = skyltarAvstandExtraObjects;
    }

    public String getPictogram() {
        return pictogram;
    }

    public void setPictogram(String pictogram) {
        this.pictogram = pictogram;
    }

    public String getPictogramExtraObjects() {
        return pictogramExtraObjects;
    }

    public void setPictogramExtraObjects(String pictogramExtraObjects) {
        this.pictogramExtraObjects = pictogramExtraObjects;
    }

    public String getKontrast() {
        return kontrast;
    }

    public void setKontrast(String kontrast) {
        this.kontrast = kontrast;
    }

    public String getKontrastExtraObjects() {
        return kontrastExtraObjects;
    }

    public void setKontrastExtraObjects(String kontrastExtraObjects) {
        this.kontrastExtraObjects = kontrastExtraObjects;
    }

    public String getEnhetlig() {
        return enhetlig;
    }

    public void setEnhetlig(String enhetlig) {
        this.enhetlig = enhetlig;
    }

    public String getEnhetligExtraObjects() {
        return enhetligExtraObjects;
    }

    public void setEnhetligExtraObjects(String enhetligExtraObjects) {
        this.enhetligExtraObjects = enhetligExtraObjects;
    }

    public String getSynligIAllaBelysningsforhallanden() {
        return synligIAllaBelysningsforhallanden;
    }

    public void setSynligIAllaBelysningsforhallanden(String synligIAllaBelysningsforhallanden) {
        this.synligIAllaBelysningsforhallanden = synligIAllaBelysningsforhallanden;
    }

    public String getSynligIAllaBelysningsforhallandenExtraObjects() {
        return synligIAllaBelysningsforhallandenExtraObjects;
    }

    public void setSynligIAllaBelysningsforhallandenExtraObjects(String synligIAllaBelysningsforhallandenExtraObjects) {
        this.synligIAllaBelysningsforhallandenExtraObjects = synligIAllaBelysningsforhallandenExtraObjects;
    }

    public String getSkyltarEnligtISO() {
        return skyltarEnligtISO;
    }

    public void setSkyltarEnligtISO(String skyltarEnligtISO) {
        this.skyltarEnligtISO = skyltarEnligtISO;
    }

    public String getSkyltarEnligtISOExtraObjects() {
        return skyltarEnligtISOExtraObjects;
    }

    public void setSkyltarEnligtISOExtraObjects(String skyltarEnligtISOExtraObjects) {
        this.skyltarEnligtISOExtraObjects = skyltarEnligtISOExtraObjects;
    }

    public String getStipaNiva() {
        return stipaNiva;
    }

    public void setStipaNiva(String stipaNiva) {
        this.stipaNiva = stipaNiva;
    }

    public String getStipaNivaExtraObjects() {
        return stipaNivaExtraObjects;
    }

    public void setStipaNivaExtraObjects(String stipaNivaExtraObjects) {
        this.stipaNivaExtraObjects = stipaNivaExtraObjects;
    }

    public String getForekomstAvRiskomrade() {
        return forekomstAvRiskomrade;
    }

    public void setForekomstAvRiskomrade(String forekomstAvRiskomrade) {
        this.forekomstAvRiskomrade = forekomstAvRiskomrade;
    }

    public String getForekomstAvRiskomradeExtraObjects() {
        return forekomstAvRiskomradeExtraObjects;
    }

    public void setForekomstAvRiskomradeExtraObjects(String forekomstAvRiskomradeExtraObjects) {
        this.forekomstAvRiskomradeExtraObjects = forekomstAvRiskomradeExtraObjects;
    }

    public String getPlattformsMinstaBredd() {
        return plattformsMinstaBredd;
    }

    public void setPlattformsMinstaBredd(String plattformsMinstaBredd) {
        this.plattformsMinstaBredd = plattformsMinstaBredd;
    }

    public String getPlattformsMinstaBreddExtraObjects() {
        return plattformsMinstaBreddExtraObjects;
    }

    public void setPlattformsMinstaBreddExtraObjects(String plattformsMinstaBreddExtraObjects) {
        this.plattformsMinstaBreddExtraObjects = plattformsMinstaBreddExtraObjects;
    }

    public String getAvstandMellanLitetHinder() {
        return avstandMellanLitetHinder;
    }

    public void setAvstandMellanLitetHinder(String avstandMellanLitetHinder) {
        this.avstandMellanLitetHinder = avstandMellanLitetHinder;
    }

    public String getAvstandMellanLitetHinderExtraObjects() {
        return avstandMellanLitetHinderExtraObjects;
    }

    public void setAvstandMellanLitetHinderExtraObjects(String avstandMellanLitetHinderExtraObjects) {
        this.avstandMellanLitetHinderExtraObjects = avstandMellanLitetHinderExtraObjects;
    }

    public String getAvstandMellanStortHinder() {
        return avstandMellanStortHinder;
    }

    public void setAvstandMellanStortHinder(String avstandMellanStortHinder) {
        this.avstandMellanStortHinder = avstandMellanStortHinder;
    }

    public String getAvstandMellanStortHinderExtraObjects() {
        return avstandMellanStortHinderExtraObjects;
    }

    public void setAvstandMellanStortHinderExtraObjects(String avstandMellanStortHinderExtraObjects) {
        this.avstandMellanStortHinderExtraObjects = avstandMellanStortHinderExtraObjects;
    }

    public String getMarkeringAvRiskomradetsGrans() {
        return markeringAvRiskomradetsGrans;
    }

    public void setMarkeringAvRiskomradetsGrans(String markeringAvRiskomradetsGrans) {
        this.markeringAvRiskomradetsGrans = markeringAvRiskomradetsGrans;
    }

    public String getMarkeringAvRiskomradetsGransExtraObjects() {
        return markeringAvRiskomradetsGransExtraObjects;
    }

    public void setMarkeringAvRiskomradetsGransExtraObjects(String markeringAvRiskomradetsGransExtraObjects) {
        this.markeringAvRiskomradetsGransExtraObjects = markeringAvRiskomradetsGransExtraObjects;
    }

    public String getBreddenPaVarningslinjeOchHalksakerhetOchFarg() {
        return breddenPaVarningslinjeOchHalksakerhetOchFarg;
    }

    public void setBreddenPaVarningslinjeOchHalksakerhetOchFarg(String breddenPaVarningslinjeOchHalksakerhetOchFarg) {
        this.breddenPaVarningslinjeOchHalksakerhetOchFarg = breddenPaVarningslinjeOchHalksakerhetOchFarg;
    }

    public String getBreddenPaVarningslinjeOchHalksakerhetOchFargExtraObjects() {
        return breddenPaVarningslinjeOchHalksakerhetOchFargExtraObjects;
    }

    public void setBreddenPaVarningslinjeOchHalksakerhetOchFargExtraObjects(String breddenPaVarningslinjeOchHalksakerhetOchFargExtraObjects) {
        this.breddenPaVarningslinjeOchHalksakerhetOchFargExtraObjects = breddenPaVarningslinjeOchHalksakerhetOchFargExtraObjects;
    }

    public String getMaterialPaPlattformskanten() {
        return materialPaPlattformskanten;
    }

    public void setMaterialPaPlattformskanten(String materialPaPlattformskanten) {
        this.materialPaPlattformskanten = materialPaPlattformskanten;
    }

    public String getMaterialPaPlattformskantenExtraObjects() {
        return materialPaPlattformskantenExtraObjects;
    }

    public void setMaterialPaPlattformskantenExtraObjects(String materialPaPlattformskantenExtraObjects) {
        this.materialPaPlattformskantenExtraObjects = materialPaPlattformskantenExtraObjects;
    }

    public String getMarkeringAvPlattformensSlut() {
        return markeringAvPlattformensSlut;
    }

    public void setMarkeringAvPlattformensSlut(String markeringAvPlattformensSlut) {
        this.markeringAvPlattformensSlut = markeringAvPlattformensSlut;
    }

    public String getMarkeringAvPlattformensSlutExtraObjects() {
        return markeringAvPlattformensSlutExtraObjects;
    }

    public void setMarkeringAvPlattformensSlutExtraObjects(String markeringAvPlattformensSlutExtraObjects) {
        this.markeringAvPlattformensSlutExtraObjects = markeringAvPlattformensSlutExtraObjects;
    }

    public String getAnvandsSomEnDelAvTrappstegfriGangvag() {
        return anvandsSomEnDelAvTrappstegfriGangvag;
    }

    public void setAnvandsSomEnDelAvTrappstegfriGangvag(String anvandsSomEnDelAvTrappstegfriGangvag) {
        this.anvandsSomEnDelAvTrappstegfriGangvag = anvandsSomEnDelAvTrappstegfriGangvag;
    }

    public String getAnvandsSomEnDelAvTrappstegfriGangvagExtraObjects() {
        return anvandsSomEnDelAvTrappstegfriGangvagExtraObjects;
    }

    public void setAnvandsSomEnDelAvTrappstegfriGangvagExtraObjects(String anvandsSomEnDelAvTrappstegfriGangvagExtraObjects) {
        this.anvandsSomEnDelAvTrappstegfriGangvagExtraObjects = anvandsSomEnDelAvTrappstegfriGangvagExtraObjects;
    }

    public String getBreddPaGangvagg() {
        return breddPaGangvagg;
    }

    public void setBreddPaGangvagg(String breddPaGangvagg) {
        this.breddPaGangvagg = breddPaGangvagg;
    }

    public String getBreddPaGangvaggExtraObjects() {
        return breddPaGangvaggExtraObjects;
    }

    public void setBreddPaGangvaggExtraObjects(String breddPaGangvaggExtraObjects) {
        this.breddPaGangvaggExtraObjects = breddPaGangvaggExtraObjects;
    }

    public String getLutning() {
        return lutning;
    }

    public void setLutning(String lutning) {
        this.lutning = lutning;
    }

    public String getLutningExtraObjects() {
        return lutningExtraObjects;
    }

    public void setLutningExtraObjects(String lutningExtraObjects) {
        this.lutningExtraObjects = lutningExtraObjects;
    }

    public String getFriPassageForMinstaHjuletPaEnRullstol() {
        return friPassageForMinstaHjuletPaEnRullstol;
    }

    public void setFriPassageForMinstaHjuletPaEnRullstol(String friPassageForMinstaHjuletPaEnRullstol) {
        this.friPassageForMinstaHjuletPaEnRullstol = friPassageForMinstaHjuletPaEnRullstol;
    }

    public String getFriPassageForMinstaHjuletPaEnRullstolExtraObjects() {
        return friPassageForMinstaHjuletPaEnRullstolExtraObjects;
    }

    public void setFriPassageForMinstaHjuletPaEnRullstolExtraObjects(String friPassageForMinstaHjuletPaEnRullstolExtraObjects) {
        this.friPassageForMinstaHjuletPaEnRullstolExtraObjects = friPassageForMinstaHjuletPaEnRullstolExtraObjects;
    }

    public String getFriPassageOmSakerhetschikanerForekommer() {
        return friPassageOmSakerhetschikanerForekommer;
    }

    public void setFriPassageOmSakerhetschikanerForekommer(String friPassageOmSakerhetschikanerForekommer) {
        this.friPassageOmSakerhetschikanerForekommer = friPassageOmSakerhetschikanerForekommer;
    }

    public String getFriPassageOmSakerhetschikanerForekommerExtraObjects() {
        return friPassageOmSakerhetschikanerForekommerExtraObjects;
    }

    public void setFriPassageOmSakerhetschikanerForekommerExtraObjects(String friPassageOmSakerhetschikanerForekommerExtraObjects) {
        this.friPassageOmSakerhetschikanerForekommerExtraObjects = friPassageOmSakerhetschikanerForekommerExtraObjects;
    }

    public String getMarkeringAvGangbaneytan() {
        return markeringAvGangbaneytan;
    }

    public void setMarkeringAvGangbaneytan(String markeringAvGangbaneytan) {
        this.markeringAvGangbaneytan = markeringAvGangbaneytan;
    }

    public String getMarkeringAvGangbaneytanExtraObjects() {
        return markeringAvGangbaneytanExtraObjects;
    }

    public void setMarkeringAvGangbaneytanExtraObjects(String markeringAvGangbaneytanExtraObjects) {
        this.markeringAvGangbaneytanExtraObjects = markeringAvGangbaneytanExtraObjects;
    }

    public String getSakerPassage() {
        return sakerPassage;
    }

    public void setSakerPassage(String sakerPassage) {
        this.sakerPassage = sakerPassage;
    }

    public String getSakerPassageExtraObjects() {
        return sakerPassageExtraObjects;
    }

    public void setSakerPassageExtraObjects(String sakerPassageExtraObjects) {
        this.sakerPassageExtraObjects = sakerPassageExtraObjects;
    }

}
