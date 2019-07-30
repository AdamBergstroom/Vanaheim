package se.vanaheim.vanaheim.data;

import android.provider.BaseColumns;

public final class ContractPropertyListDB {

    private ContractPropertyListDB() {}

    public static final class PropertyListEntry implements BaseColumns {

        public final static String _ID = BaseColumns._ID;
        public final static String TABLE_NAME_PROPERTY_LIST = "propertyListTable";

        //************ 4.2.1.1 Parkeringsmöjligheter för funktionshindrade *************1
        public final static String COLUMN_1_1 ="parkeringsmojligheterForFunktionshindrade";
        public final static String COLUMN_1_1_EXTRA_OBJECTS ="parkeringsmojligheterForFunktionshindradeExtraObjects";
        public final static String COLUMN_1_2 ="placeringAvParkeringForFunktionshindrade";
        public final static String COLUMN_1_2_EXtRA_OBJECtS ="placeringAvParkeringForFunktionshindradeExtraObjects";

        //************ 4.2.1.2 Hinderfri gångväg*************2
        public final static String COLUMN_2_1 ="forekomstAvHinderfriGangvag";
        public final static String COLUMN_2_1_EXTRA_OBJECTS ="forekomstAvHinderfriGangvagExtraObjects";
        public final static String COLUMN_2_2 ="langdenPaHindergriaGangvagar";
        public final static String COLUMN_2_2_EXTRA_OBJECTS ="langdenPaHindergriaGangvagarExtraObjects";
        public final static String COLUMN_2_3 ="reflekterandeEgenskaper";
        public final static String COLUMN_2_3_EXTRA_OBJECTS ="reflekterandeEgenskaperExtraObjects";

        //************ 4.2.1.2.1 Horisontell förflyttning************3
        public final static String COLUMN_3_1 ="hinderfriGangvagsbredd";
        public final static String COLUMN_3_1_EXTRA_OBJECTS ="hinderfriGangvagsbreddExtraObjects";
        public final static String COLUMN_3_2 ="trosklarPaHinderfriGangvag";
        public final static String COLUMN_3_2_EXTRA_OBJECTS ="trosklarPaHinderfriGangvagExtraObjects";

        //************ 4.2.1.2.2 Vertikal förflyttning************4
        public final static String COLUMN_4_1 ="trappstegsfriVag";
        public final static String COLUMN_4_1_EXTRA_OBJECTS ="trappstegsfriVagExtraObjects";
        public final static String COLUMN_4_2 ="breddPaTrappor";
        public final static String COLUMN_4_2_EXTRA_OBJECTS ="breddPaTrapporExtraObjects";
        public final static String COLUMN_4_3 ="visuelMarkeringPaForstaOchSistaSteget";
        public final static String COLUMN_4_3_EXTRA_OBJECTS ="visuelMarkeringPaForstaOchSistaStegetExtraObjects";
        public final static String COLUMN_4_4 ="taktilVarningForeForstaMedFunktionsnedsattningar";
        public final static String COLUMN_4_4_EXTRA_OBJECTS ="taktilVarningForeForstaMedFunktionsnedsattningarExtraObjects";
        public final static String COLUMN_4_5 ="ramperForPersonerMedFunktionsnedsättningar";
        public final static String COLUMN_4_5_EXTRA_OBJECTS ="ramperForPersonerMedFunktionsnedsättningarExtraObjects";
        public final static String COLUMN_4_6 ="ledstangerPaBadaSidorOchTvaNivaer";
        public final static String COLUMN_4_6_EXTRA_OBJECTS ="ledstangerPaBadaSidorOchTvaNivaerExtraObjects";
        public final static String COLUMN_4_7 ="hissar";
        public final static String COLUMN_4_7_EXTRA_OBJECTS ="hissarExtraObjects";
        public final static String COLUMN_4_8 ="rulltrapporOchRullramper";
        public final static String COLUMN_4_8_EXTRA_OBJECTS ="rulltrapporOchRullramperExtraObjects";
        public final static String COLUMN_4_9 ="plankorsningar";
        public final static String COLUMN_4_9_EXTRA_OBJECTS ="plankorsningarExtraObjects";

        //************ 4.2.1.2.3 Gångvägsmarkering************5
        public final static String COLUMN_5_1 ="tydligMarkering";
        public final static String COLUMN_5_1_EXTRA_OBJECTS ="tydligMarkeringExtraObjects";
        public final static String COLUMN_5_2 ="tillhandahållandeAvInformationTillSynskadade";
        public final static String COLUMN_5_2_EXTRA_OBJECTS ="tillhandahållandeAvInformationTillSynskadadeExtraObjects";
        public final static String COLUMN_5_3 ="fjarrstyrdaLjudanordningarEllerTeleapplikationer";
        public final static String COLUMN_5_3_EXTRA_OBJECTS ="fjarrstyrdaLjudanordningarEllerTeleapplikationerExtraObjects";
        public final static String COLUMN_5_4 ="taktilInformationPaLedstangerEllerVaggar";
        public final static String COLUMN_5_4_EXTRA_OBJECTS ="taktilInformationPaLedstangerEllerVaggarExtraObjects";

        //************ 4.2.1.4 Golvytor************6
        public final static String COLUMN_6_1 ="halksakerhet";
        public final static String COLUMN_6_1_EXTRA_OBJECTS ="halksakerhetExtraObjects";
        public final static String COLUMN_6_2 ="ojamnheterSomOverstiger";
        public final static String COLUMN_6_2_EXTRA_OBJECTS ="ojamnheterSomOverstigerExtraObjects";

        //************ 4.2.1.5 Markering av genomskinliga hinder************7
        public final static String COLUMN_7_1 ="glasdorrarEllerGenomskinligaVaggarLangsGangvagar";
        public final static String COLUMN_7_1_EXTRA_OBJECTS ="glasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraObjects";

        //************ 4.2.1.6 Toaletter och skötplatser************8
        public final static String COLUMN_8_1 ="toalettutrymmeAnpassatForRullstolsburnaPersoner";
        public final static String COLUMN_8_1_EXTRA_OBJECTS ="toalettutrymmeAnpassatForRullstolsburnaPersonerExtraObjects";
        public final static String COLUMN_8_2 ="skotplatserTillgangligaForBadeKonen";
        public final static String COLUMN_8_2_EXTRA_OBJECTS ="skotplatserTillgangligaForBadeKonenExtraObjects";

        //************ 4.2.1.7 Inredning och fristående enheter************9
        public final static String COLUMN_9_1 ="kontrastMotBakgrundOchAvrundandeKanter";
        public final static String COLUMN_9_1_EXTRA_OBJECTS ="kontrastMotBakgrundOchAvrundandeKanterExtraObjects";
        public final static String COLUMN_9_2 ="placeringAvInredningOchEnheter";
        public final static String COLUMN_9_2_EXTRA_OBJECTS ="placeringAvInredningOchEnheterExtraObjects";
        public final static String COLUMN_9_3 ="sittmojligheterOchPlatsForEnRullstolsbundenPerson";
        public final static String COLUMN_9_3_EXTRA_OBJECTS ="sittmojligheterOchPlatsForEnRullstolsbundenPersonExtraObjects";
        public final static String COLUMN_9_4 ="vaderskyddatOmrade";
        public final static String COLUMN_9_4_EXTRA_OBJECTS ="vaderskyddatOmradeExtraObjects";
        //************4.2.1.9 Belysning************10
        public final static String COLUMN_10_1 ="belysningPaStationensExternaOmraden";
        public final static String COLUMN_10_1_EXTRA_OBJECTS ="belysningPaStationensExternaOmradenExtraObjects";
        public final static String COLUMN_10_2 ="belysningLangsHinderfriaGangvagar";
        public final static String COLUMN_10_2_EXTRA_OBJECTS ="belysningLangsHinderfriaGangvagarExtraObjects";
        public final static String COLUMN_10_3 ="belysningPaPlattformar";
        public final static String COLUMN_10_3_EXTRA_OBJECTS ="belysningPaPlattformarExtraObjects";
        public final static String COLUMN_10_4 ="nodbelysning";
        public final static String COLUMN_10_4_EXTRA_OBJECTS ="nodbelysningExtraObjects";

        //************4.2.1.10 Visuell information************11
        public final static String COLUMN_11_1 ="skyltarAvstand";
        public final static String COLUMN_11_1_EXTRA_OBJECTS ="skyltarAvstandExtraObjects";
        public final static String COLUMN_11_2 ="pictogram";
        public final static String COLUMN_11_2_EXTRA_OBJECTS ="pictogramExtraObjects";
        public final static String COLUMN_11_3 ="kontrast";
        public final static String COLUMN_11_3_EXTRA_OBJECTS ="kontrastExtraObjects";
        public final static String COLUMN_11_4 ="enhetlig";
        public final static String COLUMN_11_4_EXTRA_OBJECTS ="enhetligExtraObjects";
        public final static String COLUMN_11_5 ="synligIAllaBelysningsforhallanden";
        public final static String COLUMN_11_5_EXTRA_OBJECTS ="synligIAllaBelysningsforhallandenExtraObjects";
        public final static String COLUMN_11_6 ="skyltarEnligtISO";
        public final static String COLUMN_11_6_EXTRA_OBJECTS ="skyltarEnligtISOExtraObjects";

        //************4.2.1.11 Talad information Sidoplattform***********12
        public final static String COLUMN_12_1 ="stipaNiva";
        public final static String COLUMN_12_1_EXTRA_OBJECTS ="stipaNivaExtraObjects";

        //************4.2.1.12 Plattformsbredd och plattformskant************13
        public final static String COLUMN_13_1 ="forekomstAvRiskomrade";
        public final static String COLUMN_13_1_EXTRA_OBJECTS ="forekomstAvRiskomradeExtraObjects";
        public final static String COLUMN_13_2 ="plattformsMinstaBredd";
        public final static String COLUMN_13_2_EXTRA_OBJECTS ="plattformsMinstaBreddExtraObjects";
        public final static String COLUMN_13_3 ="avstandMellanLitetHinder";
        public final static String COLUMN_13_3_EXTRA_OBJECTS ="avstandMellanLitetHinderExtraObjects";
        public final static String COLUMN_13_4 ="avstandMellanStortHinder";
        public final static String COLUMN_13_4_EXTRA_OBJECTS ="avstandMellanStortHinderExtraObjects";
        public final static String COLUMN_13_5 ="markeringAvRiskomradetsGrans";
        public final static String COLUMN_13_5_EXTRA_OBJECTS ="markeringAvRiskomradetsGransExtraObjects";
        public final static String COLUMN_13_6 ="breddenPaVarningslinjeOchHalksakerhetOchFarg";
        public final static String COLUMN_13_6_EXTRA_OBJECTS ="breddenPaVarningslinjeOchHalksakerhetOchFargExtraObjects";
        public final static String COLUMN_13_7 ="materialPaPlattformskanten";
        public final static String COLUMN_13_7_EXTRA_OBJECTS ="materialPaPlattformskantenExtraObjects";

        //************4.2.1.13 Plattformens slut************14
        public final static String COLUMN_14_1 ="markeringAvPlattformensSlut";
        public final static String COLUMN_14_1_EXTRA_OBJECTS ="markeringAvPlattformensSlutExtraObjects";

        //************4.2.1.15 Spårkorsning för passagerare påväg till plattformar************15
        public final static String COLUMN_15_1 ="anvandsSomEnDelAvTrappstegfriGangvag";
        public final static String COLUMN_15_1_EXTRA_OBJECTS ="anvandsSomEnDelAvTrappstegfriGangvagExtraObjects";
        public final static String COLUMN_15_2 ="breddPaGangvag";
        public final static String COLUMN_15_2_EXTRA_OBJECTS ="breddPaGangvagExtraObjects";
        public final static String COLUMN_15_3 ="lutning";
        public final static String COLUMN_15_3_EXTRA_OBJECTS ="lutningExtraObjects";
        public final static String COLUMN_15_4 ="friPassageForMinstaHjuletPaEnRullstol";
        public final static String COLUMN_15_4_EXTRA_OBJECTS ="friPassageForMinstaHjuletPaEnRullstolExtraObjects";
        public final static String COLUMN_15_5 ="friPassageOmSakerhetschikanerForekommer";
        public final static String COLUMN_15_5_EXTRA_OBJECTS ="friPassageOmSakerhetschikanerForekommerExtraObjects";
        public final static String COLUMN_15_6 ="markeringAvGangbaneytan";
        public final static String COLUMN_15_6_EXTRA_OBJECTS ="markeringAvGangbaneytanExtraObjects";
        public final static String COLUMN_15_7 ="sakerPassage";
        public final static String COLUMN_15_7_EXTRA_OBJECTS ="sakerPassageExtraObjects";
    }
}