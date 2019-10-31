package se.vanaheim.vanaheim;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import se.vanaheim.vanaheim.data.HandleDatabases;
import se.vanaheim.vanaheim.models.PropertyListObjects;

public class EditPopertyListObjectActivity extends AppCompatActivity {

    //************ 4.2.1.1 Parkeringsmöjligheter för funktionshindrade *************
    private EditText parkeringsmojligheterForFunktionshindrade;
    private EditText parkeringsmojligheterForFunktionshindradeExtraName;
    private EditText parkeringsmojligheterForFunktionshindradeExtraStatus;
    private ImageButton parkeringsmojligheterForFunktionshindradeSaveExtraObject;
    private TextView parkeringsmojligheterForFunktionshindradeShowExtraObjects;
    private ImageButton parkeringsmojligheterForFunktionshindradeTrash;

    private EditText placeringAvParkeringForFunktionshindrade;
    private EditText placeringAvParkeringForFunktionshindradeExtraName;
    private EditText placeringAvParkeringForFunktionshindradeExtraStatus;
    private ImageButton placeringAvParkeringForFunktionshindradeSaveExtraObject;
    private TextView placeringAvParkeringForFunktionshindradeShowExtraObjects;
    private ImageButton placeringAvParkeringForFunktionshindradeTrash;
    //************ 4.2.1.2 Hinderfri gångväg*************
    private EditText forekomstAvHinderfriGangvag;
    private EditText forekomstAvHinderfriGangvagExtraName;
    private EditText forekomstAvHinderfriGangvagExtraStatus;
    private ImageButton forekomstAvHinderfriGangvagSaveExtraObject;
    private TextView forekomstAvHinderfriGangvagShowExtraObjects;
    private ImageButton forekomstAvHinderfriGangvagTrash;

    private EditText langdenPaHindergriaGangvagar;
    private EditText langdenPaHindergriaGangvagarExtraName;
    private EditText langdenPaHindergriaGangvagarExtraStatus;
    private ImageButton langdenPaHindergriaGangvagarSaveExtraObject;
    private TextView langdenPaHindergriaGangvagarShowExtraObjects;
    private ImageButton langdenPaHindergriaGangvagarTrash;

    private EditText reflekterandeEgenskaper;
    private EditText reflekterandeEgenskaperExtraName;
    private EditText reflekterandeEgenskaperExtraStatus;
    private ImageButton reflekterandeEgenskaperSaveExtraObject;
    private TextView reflekterandeEgenskaperShowExtraObjects;
    private ImageButton reflekterandeEgenskaperTrash;

    //************ 4.2.1.2.1 Horisontell förflyttning************
    private EditText hinderfriGangvagsbredd;
    private EditText hinderfriGangvagsbreddExtraName;
    private EditText hinderfriGangvagsbreddExtraStatus;
    private ImageButton hinderfriGangvagsbreddSaveExtraObject;
    private TextView hinderfriGangvagsbreddShowExtraObjects;
    private ImageButton hinderfriGangvagsbreddTrash;
    private EditText trosklarPaHinderfriGangvag;

    private EditText trosklarPaHinderfriGangvagExtraName;
    private EditText trosklarPaHinderfriGangvagExtraStatus;
    private ImageButton trosklarPaHinderfriGangvagSaveExtraObject;
    private TextView trosklarPaHinderfriGangvagShowExtraObjects;
    private ImageButton trosklarPaHinderfriGangvagTrash;

    //************ 4.2.1.2.2 Vertikal förflyttning************
    private EditText trappstegsfriVag;
    private EditText trappstegsfriVagExtraName;
    private EditText trappstegsfriVagExtraStatus;
    private ImageButton trappstegsfriVagSaveExtraObject;
    private TextView trappstegsfriVagShowExtraObjects;
    private ImageButton trappstegsfriVagTrash;

    private EditText breddPaTrappor;
    private EditText breddPaTrapporExtraName;
    private EditText breddPaTrapporExtraStatus;
    private ImageButton breddPaTrapporSaveExtraObject;
    private TextView breddPaTrapporShowExtraObjects;
    private ImageButton breddPaTrapporTrash;

    private EditText visuelMarkeringPaForstaOchSistaSteget;
    private EditText visuelMarkeringPaForstaOchSistaStegetExtraName;
    private EditText visuelMarkeringPaForstaOchSistaStegetExtraStatus;
    private ImageButton visuelMarkeringPaForstaOchSistaStegetSaveExtraObject;
    private TextView visuelMarkeringPaForstaOchSistaStegetShowExtraObjects;
    private ImageButton visuelMarkeringPaForstaOchSistaStegetTrash;

    private EditText taktilVarningForeForstaUppgaendeTrappsteg;
    private EditText taktilVarningForeForstaUppgaendeTrappstegExtraName;
    private EditText taktilVarningForeForstaUppgaendeTrappstegExtraStatus;
    private ImageButton taktilVarningForeForstaUppgaendeTrappstegSaveExtraObject;
    private TextView taktilVarningForeForstaUppgaendeTrappstegShowExtraObjects;
    private ImageButton taktilVarningForeForstaUppgaendeTrappstegTrash;

    private EditText ramperForPersonerMedFunktionsnedsättningar;
    private EditText ramperForPersonerMedFunktionsnedsättningarExtraName;
    private EditText ramperForPersonerMedFunktionsnedsättningarExtraStatus;
    private ImageButton ramperForPersonerMedFunktionsnedsättningarSaveExtraObject;
    private TextView ramperForPersonerMedFunktionsnedsättningarShowExtraObjects;
    private ImageButton ramperForPersonerMedFunktionsnedsättningarTrash;

    private EditText ledstangerPaBadaSidorOchTvaNivaer;
    private EditText ledstangerPaBadaSidorOchTvaNivaerExtraName;
    private EditText ledstangerPaBadaSidorOchTvaNivaerExtraStatus;
    private ImageButton ledstangerPaBadaSidorOchTvaNivaerSaveExtraObject;
    private TextView ledstangerPaBadaSidorOchTvaNivaerShowExtraObjects;
    private ImageButton ledstangerPaBadaSidorOchTvaNivaerTrash;

    private EditText hissar;
    private EditText hissarExtraName;
    private EditText hissarExtraStatus;
    private ImageButton hissarSaveExtraObject;
    private TextView hissarShowExtraObjects;
    private ImageButton hissarTrash;

    private EditText rulltrapporOchRullramper;
    private EditText rulltrapporOchRullramperExtraName;
    private EditText rulltrapporOchRullramperExtraStatus;
    private ImageButton rulltrapporOchRullramperSaveExtraObject;
    private TextView rulltrapporOchRullramperShowExtraObjects;
    private ImageButton rulltrapporOchRullramperTrash;

    private EditText plankorsningar;
    private EditText plankorsningarExtraName;
    private EditText plankorsningarExtraStatus;
    private ImageButton plankorsningarSaveExtraObject;
    private TextView plankorsningarShowExtraObjects;
    private ImageButton plankorsningarTrash;

    //************ 4.2.1.2.3 Gångvägsmarkering************
    private EditText tydligMarkering;
    private EditText tydligMarkeringExtraName;
    private EditText tydligMarkeringExtraStatus;
    private ImageButton tydligMarkeringSaveExtraObject;
    private TextView tydligMarkeringShowExtraObjects;
    private ImageButton tydligMarkeringTrash;

    private EditText tillhandahållandeAvInformationTillSynskadade;
    private EditText tillhandahållandeAvInformationTillSynskadadeExtraName;
    private EditText tillhandahållandeAvInformationTillSynskadadeExtraStatus;
    private ImageButton tillhandahållandeAvInformationTillSynskadadeSaveExtraObject;
    private TextView tillhandahållandeAvInformationTillSynskadadeShowExtraObjects;
    private ImageButton tillhandahållandeAvInformationTillSynskadadeTrash;

    private EditText fjarrstyrdaLjudanordningarEllerTeleapplikationer;
    private EditText fjarrstyrdaLjudanordningarEllerTeleapplikationerExtraName;
    private EditText fjarrstyrdaLjudanordningarEllerTeleapplikationerExtraStatus;
    private ImageButton fjarrstyrdaLjudanordningarEllerTeleapplikationerSaveExtraObject;
    private TextView fjarrstyrdaLjudanordningarEllerTeleapplikationerShowExtraObjects;
    private ImageButton fjarrstyrdaLjudanordningarEllerTeleapplikationerTrash;

    private EditText taktilInformationPaLedstangerEllerVaggar;
    private EditText taktilInformationPaLedstangerEllerVaggarExtraName;
    private EditText taktilInformationPaLedstangerEllerVaggarExtraStatus;
    private ImageButton taktilInformationPaLedstangerEllerVaggarSaveExtraObject;
    private TextView taktilInformationPaLedstangerEllerVaggarShowExtraObjects;
    private ImageButton taktilInformationPaLedstangerEllerVaggarTrash;

    //************ 4.2.1.4 Golvytor************
    private EditText halksakerhet;
    private EditText halksakerhetExtraName;
    private EditText halksakerhetExtraStatus;
    private ImageButton halksakerhetSaveExtraObject;
    private TextView halksakerhetShowExtraObjects;
    private ImageButton halksakerhetTrash;

    private EditText ojamnheterSomOverstiger;
    private EditText ojamnheterSomOverstigerExtraName;
    private EditText ojamnheterSomOverstigerExtraStatus;
    private ImageButton ojamnheterSomOverstigerSaveExtraObject;
    private TextView ojamnheterSomOverstigerShowExtraObjects;
    private ImageButton ojamnheterSomOverstigerTrash;

    //************ 4.2.1.5 Markering av genomskinliga hinder************
    private EditText glasdorrarEllerGenomskinligaVaggarLangsGangvagar;
    private EditText glasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraName;
    private EditText glasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraStatus;
    private ImageButton glasdorrarEllerGenomskinligaVaggarLangsGangvagarSaveExtraObject;
    private TextView glasdorrarEllerGenomskinligaVaggarLangsGangvagarShowExtraObjects;
    private ImageButton glasdorrarEllerGenomskinligaVaggarLangsGangvagarTrash;

    //************ 4.2.1.6 Toaletter och skötplatser************
    private EditText toalettutrymmeAnpassatForRullstolsburnaPersoner;
    private EditText toalettutrymmeAnpassatForRullstolsburnaPersonerExtraName;
    private EditText toalettutrymmeAnpassatForRullstolsburnaPersonerExtraStatus;
    private ImageButton toalettutrymmeAnpassatForRullstolsburnaPersonerSaveExtraObject;
    private TextView toalettutrymmeAnpassatForRullstolsburnaPersonerShowExtraObjects;
    private ImageButton toalettutrymmeAnpassatForRullstolsburnaPersonerTrash;

    private EditText skotplatserTillgangligaForBadeKonen;
    private EditText skotplatserTillgangligaForBadeKonenExtraName;
    private EditText skotplatserTillgangligaForBadeKonenExtraStatus;
    private ImageButton skotplatserTillgangligaForBadeKonenSaveExtraObject;
    private TextView skotplatserTillgangligaForBadeKonenShowExtraObjects;
    private ImageButton skotplatserTillgangligaForBadeKonenTrash;

    //************ 4.2.1.7 Inredning och fristående enheter************
    private EditText kontrastMotBakgrundOchAvrundandeKanter;
    private EditText kontrastMotBakgrundOchAvrundandeKanterExtraName;
    private EditText kontrastMotBakgrundOchAvrundandeKanterExtraStatus;
    private ImageButton kontrastMotBakgrundOchAvrundandeKanterSaveExtraObject;
    private TextView kontrastMotBakgrundOchAvrundandeKanterShowExtraObjects;
    private ImageButton kontrastMotBakgrundOchAvrundandeKanterTrash;

    private EditText placeringAvInredningOchEnheter;
    private EditText placeringAvInredningOchEnheterExtraName;
    private EditText placeringAvInredningOchEnheterExtraStatus;
    private ImageButton placeringAvInredningOchEnheterSaveExtraObject;
    private TextView placeringAvInredningOchEnheterShowExtraObjects;
    private ImageButton placeringAvInredningOchEnheterTrash;

    private EditText sittmojligheterOchPlatsForEnRullstolsbundenPerson;
    private EditText sittmojligheterOchPlatsForEnRullstolsbundenPersonExtraName;
    private EditText sittmojligheterOchPlatsForEnRullstolsbundenPersonExtraStatus;
    private ImageButton sittmojligheterOchPlatsForEnRullstolsbundenPersonSaveExtraObject;
    private TextView sittmojligheterOchPlatsForEnRullstolsbundenPersonShowExtraObjects;
    private ImageButton sittmojligheterOchPlatsForEnRullstolsbundenPersonTrash;

    private EditText vaderskyddatOmrade;
    private EditText vaderskyddatOmradeExtraName;
    private EditText vaderskyddatOmradeExtraStatus;
    private ImageButton vaderskyddatOmradeSaveExtraObject;
    private TextView vaderskyddatOmradeShowExtraObjects;
    private ImageButton vaderskyddatOmradeTrash;

    //************4.2.1.9 Belysning************
    private EditText belysningPaStationensExternaOmraden;
    private EditText belysningPaStationensExternaOmradenExtraName;
    private EditText belysningPaStationensExternaOmradenExtraStatus;
    private ImageButton belysningPaStationensExternaOmradenSaveExtraObject;
    private TextView belysningPaStationensExternaOmradenShowExtraObjects;
    private ImageButton belysningPaStationensExternaOmradenTrash;

    private EditText belysningLangsHinderfriaGangvagar;
    private EditText belysningLangsHinderfriaGangvagarExtraName;
    private EditText belysningLangsHinderfriaGangvagarExtraStatus;
    private ImageButton belysningLangsHinderfriaGangvagarSaveExtraObject;
    private TextView belysningLangsHinderfriaGangvagarShowExtraObjects;
    private ImageButton belysningLangsHinderfriaGangvagarTrash;

    private EditText belysningPaPlattformar;
    private EditText belysningPaPlattformarExtraName;
    private EditText belysningPaPlattformarExtraStatus;
    private ImageButton belysningPaPlattformarSaveExtraObject;
    private TextView belysningPaPlattformarShowExtraObjects;
    private ImageButton belysningPaPlattformarTrash;

    private EditText nodbelysning;
    private EditText nodbelysningExtraName;
    private EditText nodbelysningExtraStatus;
    private ImageButton nodbelysningSaveExtraObject;
    private TextView nodbelysningShowExtraObjects;
    private ImageButton nodbelysningTrash;

    //************4.2.1.10 Visuell information************
    private EditText skyltarAvstand;
    private EditText skyltarAvstandExtraName;
    private EditText skyltarAvstandExtraStatus;
    private ImageButton skyltarAvstandSaveExtraObject;
    private TextView skyltarAvstandShowExtraObjects;
    private ImageButton skyltarAvstandTrash;

    private EditText pictogram;
    private EditText pictogramExtraName;
    private EditText pictogramExtraStatus;
    private ImageButton pictogramSaveExtraObject;
    private TextView pictogramShowExtraObjects;
    private ImageButton pictogramTrash;

    private EditText kontrast;
    private EditText kontrastExtraName;
    private EditText kontrastExtraStatus;
    private ImageButton kontrastSaveExtraObject;
    private TextView kontrastShowExtraObjects;
    private ImageButton kontrastTrash;

    private EditText enhetlig;
    private EditText enhetligExtraName;
    private EditText enhetligExtraStatus;
    private ImageButton enhetligSaveExtraObject;
    private TextView enhetligShowExtraObjects;
    private ImageButton enhetligTrash;

    private EditText synligIAllaBelysningsforhallanden;
    private EditText synligIAllaBelysningsforhallandenExtraName;
    private EditText synligIAllaBelysningsforhallandenExtraStatus;
    private ImageButton synligIAllaBelysningsforhallandenSaveExtraObject;
    private TextView synligIAllaBelysningsforhallandenShowExtraObjects;
    private ImageButton synligIAllaBelysningsforhallandenTrash;

    private EditText skyltarEnligtISO;
    private EditText skyltarEnligtISOExtraName;
    private EditText skyltarEnligtISOExtraStatus;
    private ImageButton skyltarEnligtISOSaveExtraObject;
    private TextView skyltarEnligtISOShowExtraObjects;
    private ImageButton skyltarEnligtISOTrash;

    //************4.2.1.11 Talad information Sidoplattform***********
    private EditText stipaNiva;
    private EditText stipaNivaExtraName;
    private EditText stipaNivaExtraStatus;
    private ImageButton stipaNivaSaveExtraObject;
    private TextView stipaNivaShowExtraObjects;
    private ImageButton stipaNivaTrash;

    //************4.2.1.12 Plattformsbredd och plattformskant************
    private EditText forekomstAvRiskomrade;
    private EditText forekomstAvRiskomradeExtraName;
    private EditText forekomstAvRiskomradeExtraStatus;
    private ImageButton forekomstAvRiskomradeSaveExtraObject;
    private TextView forekomstAvRiskomradeShowExtraObjects;
    private ImageButton forekomstAvRiskomradeTrash;

    private EditText plattformsMinstaBredd;
    private EditText plattformsMinstaBreddExtraName;
    private EditText plattformsMinstaBreddExtraStatus;
    private ImageButton plattformsMinstaBreddSaveExtraObject;
    private TextView plattformsMinstaBreddShowExtraObjects;
    private ImageButton plattformsMinstaBreddTrash;

    private EditText avstandMellanLitetHinder;
    private EditText avstandMellanLitetHinderExtraName;
    private EditText avstandMellanLitetHinderExtraStatus;
    private ImageButton avstandMellanLitetHinderSaveExtraObject;
    private TextView avstandMellanLitetHinderShowExtraObjects;
    private ImageButton avstandMellanLitetHinderTrash;

    private EditText avstandMellanStortHinder;
    private EditText avstandMellanStortHinderExtraName;
    private EditText avstandMellanStortHinderExtraStatus;
    private ImageButton avstandMellanStortHinderSaveExtraObject;
    private TextView avstandMellanStortHinderShowExtraObjects;
    private ImageButton avstandMellanStortHinderTrash;

    private EditText markeringAvRiskomradetsGrans;
    private EditText markeringAvRiskomradetsGransExtraName;
    private EditText markeringAvRiskomradetsGransExtraStatus;
    private ImageButton markeringAvRiskomradetsGransSaveExtraObject;
    private TextView markeringAvRiskomradetsGransShowExtraObjects;
    private ImageButton markeringAvRiskomradetsGransTrash;

    private EditText breddenPaVarningslinjeOchHalksakerhetOchFarg;
    private EditText breddenPaVarningslinjeOchHalksakerhetOchFargExtraName;
    private EditText breddenPaVarningslinjeOchHalksakerhetOchFargExtraStatus;
    private ImageButton breddenPaVarningslinjeOchHalksakerhetOchFargSaveExtraObject;
    private TextView breddenPaVarningslinjeOchHalksakerhetOchFargShowExtraObjects;
    private ImageButton breddenPaVarningslinjeOchHalksakerhetOchFargTrash;

    private EditText materialPaPlattformskanten;
    private EditText materialPaPlattformskantenExtraName;
    private EditText materialPaPlattformskantenExtraStatus;
    private ImageButton materialPaPlattformskantenSaveExtraObject;
    private TextView materialPaPlattformskantenShowExtraObjects;
    private ImageButton materialPaPlattformskantenTrash;

    //************4.2.1.13 Plattformens slut************
    private EditText markeringAvPlattformensSlut;
    private EditText markeringAvPlattformensSlutExtraName;
    private EditText markeringAvPlattformensSlutExtraStatus;
    private ImageButton markeringAvPlattformensSlutSaveExtraObject;
    private TextView markeringAvPlattformensSlutShowExtraObjects;
    private ImageButton markeringAvPlattformensSlutTrash;

    //************4.2.1.15 Spårkorsning för passagerare påväg till plattformar************
    private EditText anvandsSomEnDelAvTrappstegfriGangvag;
    private EditText anvandsSomEnDelAvTrappstegfriGangvagExtraName;
    private EditText anvandsSomEnDelAvTrappstegfriGangvagExtraStatus;
    private ImageButton anvandsSomEnDelAvTrappstegfriGangvagSaveExtraObjects;
    private TextView anvandsSomEnDelAvTrappstegfriGangvagShowExtraObjects;
    private ImageButton anvandsSomEnDelAvTrappstegfriGangvagTrash;

    private EditText breddPaGangvagg;
    private EditText breddPaGangvaggExtraName;
    private EditText breddPaGangvaggExtraStatus;
    private ImageButton breddPaGangvaggSaveExtraObject;
    private TextView breddPaGangvaggShowExtraObjects;
    private ImageButton breddPaGangvaggTrash;

    private EditText lutning;
    private EditText lutningExtraName;
    private EditText lutningExtraStatus;
    private ImageButton lutningSaveExtraObject;
    private TextView lutningShowExtraObjects;
    private ImageButton lutningTrash;

    private EditText friPassageForMinstaHjuletPaEnRullstol;
    private EditText friPassageForMinstaHjuletPaEnRullstolExtraName;
    private EditText friPassageForMinstaHjuletPaEnRullstolExtraStatus;
    private ImageButton friPassageForMinstaHjuletPaEnRullstolSaveExtraObject;
    private TextView friPassageForMinstaHjuletPaEnRullstolShowExtraObjects;
    private ImageButton friPassageForMinstaHjuletPaEnRullstolTrash;

    private EditText friPassageOmSakerhetschikanerForekommer;
    private EditText friPassageOmSakerhetschikanerForekommerExtraName;
    private EditText friPassageOmSakerhetschikanerForekommerExtraStatus;
    private ImageButton friPassageOmSakerhetschikanerForekommerSaveExtraObject;
    private TextView friPassageOmSakerhetschikanerForekommerShowExtraObjects;
    private ImageButton friPassageOmSakerhetschikanerForekommerTrash;

    private EditText markeringAvGangbaneytan;
    private EditText markeringAvGangbaneytanExtraName;
    private EditText markeringAvGangbaneytanExtraStatus;
    private ImageButton markeringAvGangbaneytanSaveExtraObject;
    private TextView markeringAvGangbaneytanShowExtraObjects;
    private ImageButton markeringAvGangbaneytanTrash;

    private EditText sakerPassage;
    private EditText sakerPassageExtraName;
    private EditText sakerPassageExtraStatus;
    private ImageButton sakerPassageSaveExtraObject;
    private TextView sakerPassageShowExtraObjects;
    private ImageButton sakerPassageTrash;

    //********************************************
    private HandleDatabases databases;
    private PropertyListObjects propertyList;
    private int rowID;
    private Toast toast;
    private boolean removeOrNot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        removeOrNot = false;
        rowID = getIntent().getIntExtra("rowId", 0);
        databases = new HandleDatabases(this);

        //Hämta data från databasen i endast ett objekt
        propertyList = databases.recoverPropertyList();

        //Skapa en layout beroende vilket objekt man tittar på
        switch (rowID) {
            case 1:
                setContentView(R.layout.property_list_parkeringsmojligheter_for_funktionshindrade);
                parkeringsmojligheterForFunktionshindrade = findViewById(R.id.parkeringsmojligheterForFunktionshindrade);
                placeringAvParkeringForFunktionshindrade = findViewById(R.id.placeringAvParkeringForFunktionshindrade);

                parkeringsmojligheterForFunktionshindradeExtraName = findViewById(R.id.parkeringsmojligheterForFunktionshindradeExtraName);
                parkeringsmojligheterForFunktionshindradeExtraStatus = findViewById(R.id.parkeringsmojligheterForFunktionshindradeExtraStatus);
                parkeringsmojligheterForFunktionshindradeSaveExtraObject = findViewById(R.id.parkeringsmojligheterForFunktionshindradeSaveExtraObject);
                parkeringsmojligheterForFunktionshindradeShowExtraObjects = findViewById(R.id.parkeringsmojligheterForFunktionshindradeShowExtraObjects);
                parkeringsmojligheterForFunktionshindradeTrash = findViewById(R.id.parkeringsmojligheterForFunktionshindradeTrash);

                parkeringsmojligheterForFunktionshindradeSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (parkeringsmojligheterForFunktionshindradeExtraName.getText().toString().trim().length() > 0 &&
                                parkeringsmojligheterForFunktionshindradeExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getParkeringsmojligheterForFunktionshindradeExtraObjects();

                            showExtraObjects += String.valueOf(parkeringsmojligheterForFunktionshindradeExtraName.getText()) + "      " +
                                    String.valueOf(parkeringsmojligheterForFunktionshindradeExtraStatus.getText()) + "\n";

                            propertyList.setParkeringsmojligheterForFunktionshindradeExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            parkeringsmojligheterForFunktionshindradeExtraName.setText("");
                            parkeringsmojligheterForFunktionshindradeExtraStatus.setText("");

                            parkeringsmojligheterForFunktionshindradeShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                parkeringsmojligheterForFunktionshindradeTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("1_1");
                    }
                });

                placeringAvParkeringForFunktionshindradeExtraName = findViewById(R.id.placeringAvParkeringForFunktionshindradeExtraName);
                placeringAvParkeringForFunktionshindradeExtraStatus = findViewById(R.id.placeringAvParkeringForFunktionshindradeExtraStatus);
                placeringAvParkeringForFunktionshindradeSaveExtraObject = findViewById(R.id.placeringAvParkeringForFunktionshindradeSaveExtraObject);
                placeringAvParkeringForFunktionshindradeShowExtraObjects = findViewById(R.id.placeringAvParkeringForFunktionshindradeShowExtraObjects);
                placeringAvParkeringForFunktionshindradeTrash = findViewById(R.id.placeringAvParkeringForFunktionshindradeTrash);

                placeringAvParkeringForFunktionshindradeSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (placeringAvParkeringForFunktionshindradeExtraName.getText().toString().trim().length() > 0 &&
                                placeringAvParkeringForFunktionshindradeExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getPlaceringAvParkeringForFunktionshindradeExtraObjects();

                            showExtraObjects += String.valueOf(placeringAvParkeringForFunktionshindradeExtraName.getText()) + "     " +
                                    String.valueOf(placeringAvParkeringForFunktionshindradeExtraStatus.getText()) + "\n";

                            propertyList.setPlaceringAvParkeringForFunktionshindradeExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            placeringAvParkeringForFunktionshindradeExtraName.setText("");
                            placeringAvParkeringForFunktionshindradeExtraStatus.setText("");

                            placeringAvParkeringForFunktionshindradeShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                placeringAvParkeringForFunktionshindradeTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("1_2");
                    }
                });
                break;
            case 2:
                setContentView(R.layout.property_list_hinderfri_gangvag);
                forekomstAvHinderfriGangvag = findViewById(R.id.forekomstAvHinderfriGangvag);
                langdenPaHindergriaGangvagar = findViewById(R.id.langdenPaHindergriaGangvagar);
                reflekterandeEgenskaper = findViewById(R.id.reflekterandeEgenskaper);

                forekomstAvHinderfriGangvagExtraName = findViewById(R.id.forekomstAvHinderfriGangvagExtraName);
                forekomstAvHinderfriGangvagExtraStatus = findViewById(R.id.forekomstAvHinderfriGangvagExtraStatus);
                forekomstAvHinderfriGangvagSaveExtraObject = findViewById(R.id.forekomstAvHinderfriGangvagSaveExtraObject);
                forekomstAvHinderfriGangvagShowExtraObjects = findViewById(R.id.forekomstAvHinderfriGangvagShowExtraObjects);
                forekomstAvHinderfriGangvagTrash = findViewById(R.id.forekomstAvHinderfriGangvagTrash);

                forekomstAvHinderfriGangvagSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (forekomstAvHinderfriGangvagExtraName.getText().toString().trim().length() > 0 &&
                                forekomstAvHinderfriGangvagExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getForekomstAvHinderfriGangvagExtraObjects();

                            showExtraObjects += String.valueOf(forekomstAvHinderfriGangvagExtraName.getText()) + "      " +
                                    String.valueOf(forekomstAvHinderfriGangvagExtraStatus.getText()) + "\n";

                            propertyList.setForekomstAvHinderfriGangvagExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            forekomstAvHinderfriGangvagExtraName.setText("");
                            forekomstAvHinderfriGangvagExtraStatus.setText("");

                            forekomstAvHinderfriGangvagShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                forekomstAvHinderfriGangvagTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("2_1");

                    }
                });

                langdenPaHindergriaGangvagarExtraName = findViewById(R.id.langdenPaHindergriaGangvagarExtraName);
                langdenPaHindergriaGangvagarExtraStatus = findViewById(R.id.langdenPaHindergriaGangvagarExtraStatus);
                langdenPaHindergriaGangvagarSaveExtraObject = findViewById(R.id.langdenPaHindergriaGangvagarSaveExtraObject);
                langdenPaHindergriaGangvagarShowExtraObjects = findViewById(R.id.langdenPaHindergriaGangvagarShowExtraObjects);
                langdenPaHindergriaGangvagarTrash = findViewById(R.id.langdenPaHindergriaGangvagarTrash);

                langdenPaHindergriaGangvagarSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (langdenPaHindergriaGangvagarExtraName.getText().toString().trim().length() > 0 &&
                                langdenPaHindergriaGangvagarExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getLangdenPaHindergriaGangvagarExtraObjects();

                            showExtraObjects += String.valueOf(langdenPaHindergriaGangvagarExtraName.getText()) + "      " +
                                    String.valueOf(langdenPaHindergriaGangvagarExtraStatus.getText()) + "\n";

                            propertyList.setLangdenPaHindergriaGangvagarExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            langdenPaHindergriaGangvagarExtraName.setText("");
                            langdenPaHindergriaGangvagarExtraStatus.setText("");

                            langdenPaHindergriaGangvagarShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                langdenPaHindergriaGangvagarTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("2_2");

                    }
                });

                reflekterandeEgenskaperExtraName = findViewById(R.id.reflekterandeEgenskaperExtraName);
                reflekterandeEgenskaperExtraStatus = findViewById(R.id.reflekterandeEgenskaperExtraStatus);
                reflekterandeEgenskaperSaveExtraObject = findViewById(R.id.reflekterandeEgenskaperSaveExtraObjects);
                reflekterandeEgenskaperShowExtraObjects = findViewById(R.id.reflekterandeEgenskaperShowExtraObjects);
                reflekterandeEgenskaperTrash = findViewById(R.id.reflekterandeEgenskaperTrash);

                reflekterandeEgenskaperSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (reflekterandeEgenskaperExtraName.getText().toString().trim().length() > 0 &&
                                reflekterandeEgenskaperExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getReflekterandeEgenskaperExtraObjects();

                            showExtraObjects += String.valueOf(reflekterandeEgenskaperExtraName.getText()) + "      " +
                                    String.valueOf(reflekterandeEgenskaperExtraStatus.getText()) + "\n";

                            propertyList.setReflekterandeEgenskaperExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            reflekterandeEgenskaperExtraName.setText("");
                            reflekterandeEgenskaperExtraStatus.setText("");

                            reflekterandeEgenskaperShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                reflekterandeEgenskaperTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("2_3");

                    }
                });

                break;
            case 3:
                setContentView(R.layout.property_list_horisontell_forflyttning);
                hinderfriGangvagsbredd = findViewById(R.id.hinderfriGangvagsbredd);
                trosklarPaHinderfriGangvag = findViewById(R.id.trosklarPaHinderfriGangvag);

                hinderfriGangvagsbreddExtraName = findViewById(R.id.hinderfriGangvagsbreddExtraName);
                hinderfriGangvagsbreddExtraStatus = findViewById(R.id.hinderfriGangvagsbreddExtraStatus);
                hinderfriGangvagsbreddSaveExtraObject = findViewById(R.id.hinderfriGangvagsbreddSaveExtraObject);
                hinderfriGangvagsbreddShowExtraObjects = findViewById(R.id.hinderfriGangvagsbreddShowExtraObjects);
                hinderfriGangvagsbreddTrash = findViewById(R.id.hinderfriGangvagsbreddTrash);

                hinderfriGangvagsbreddSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (hinderfriGangvagsbreddExtraName.getText().toString().trim().length() > 0 &&
                                hinderfriGangvagsbreddExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getHinderfriGangvagsbreddExtraObjects();

                            showExtraObjects += String.valueOf(hinderfriGangvagsbreddExtraName.getText()) + "      " +
                                    String.valueOf(hinderfriGangvagsbreddExtraStatus.getText()) + "\n";

                            propertyList.setHinderfriGangvagsbreddExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            hinderfriGangvagsbreddExtraName.setText("");
                            hinderfriGangvagsbreddExtraStatus.setText("");

                            hinderfriGangvagsbreddShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                hinderfriGangvagsbreddTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("3_1");

                    }
                });

                trosklarPaHinderfriGangvagExtraName = findViewById(R.id.trosklarPaHinderfriGangvagExtraName);
                trosklarPaHinderfriGangvagExtraStatus = findViewById(R.id.trosklarPaHinderfriGangvagExtraStatus);
                trosklarPaHinderfriGangvagSaveExtraObject = findViewById(R.id.trosklarPaHinderfriGangvagSaveExtraObject);
                trosklarPaHinderfriGangvagShowExtraObjects = findViewById(R.id.trosklarPaHinderfriGangvagShowExtraObjects);
                trosklarPaHinderfriGangvagTrash = findViewById(R.id.trosklarPaHinderfriGangvagTrash);

                trosklarPaHinderfriGangvagSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (trosklarPaHinderfriGangvagExtraName.getText().toString().trim().length() > 0 &&
                                trosklarPaHinderfriGangvagExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getTrosklarPaHinderfriGangvagExtraObjects();

                            showExtraObjects += String.valueOf(trosklarPaHinderfriGangvagExtraName.getText()) + "      " +
                                    String.valueOf(trosklarPaHinderfriGangvagExtraStatus.getText()) + "\n";

                            propertyList.setTrosklarPaHinderfriGangvagExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            trosklarPaHinderfriGangvagExtraName.setText("");
                            trosklarPaHinderfriGangvagExtraStatus.setText("");

                            trosklarPaHinderfriGangvagShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                trosklarPaHinderfriGangvagTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("3_2");

                    }
                });

                break;
            case 4:
                setContentView(R.layout.property_list_vertikal_forflyttning);
                trappstegsfriVag = findViewById(R.id.trappstegsfriVag);
                breddPaTrappor = findViewById(R.id.breddPaTrappor);
                visuelMarkeringPaForstaOchSistaSteget = findViewById(R.id.visuelMarkeringPaForstaOchSistaSteget);
                taktilVarningForeForstaUppgaendeTrappsteg = findViewById(R.id.taktilVarningForeForstaUppgaendeTrappsteg);
                ramperForPersonerMedFunktionsnedsättningar = findViewById(R.id.ramperForPersonerMedFunktionsnedsättningar);
                ledstangerPaBadaSidorOchTvaNivaer = findViewById(R.id.ledstangerPaBadaSidorOchTvaNivaer);
                hissar = findViewById(R.id.hissar);
                rulltrapporOchRullramper = findViewById(R.id.rulltrapporOchRullramper);
                plankorsningar = findViewById(R.id.plankorsningar);

                trappstegsfriVagExtraName = findViewById(R.id.trappstegsfriVagExtraName);
                trappstegsfriVagExtraStatus = findViewById(R.id.trappstegsfriVagExtraStatus);
                trappstegsfriVagSaveExtraObject = findViewById(R.id.trappstegsfriVagSaveExtraObject);
                trappstegsfriVagShowExtraObjects = findViewById(R.id.trappstegsfriVagShowExtraObjects);
                trappstegsfriVagTrash = findViewById(R.id.trappstegsfriVagTrash);

                trappstegsfriVagSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (trappstegsfriVagExtraName.getText().toString().trim().length() > 0 &&
                                trappstegsfriVagExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getTrappstegsfriVagExtraObjects();

                            showExtraObjects += String.valueOf(trappstegsfriVagExtraName.getText()) + "      " +
                                    String.valueOf(trappstegsfriVagExtraStatus.getText()) + "\n";

                            propertyList.setTrappstegsfriVagExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            trappstegsfriVagExtraName.setText("");
                            trappstegsfriVagExtraStatus.setText("");

                            trappstegsfriVagShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                trappstegsfriVagTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("4_1");

                    }
                });

                breddPaTrapporExtraName = findViewById(R.id.breddPaTrapporExtraName);
                breddPaTrapporExtraStatus = findViewById(R.id.breddPaTrapporExtraStatus);
                breddPaTrapporSaveExtraObject = findViewById(R.id.breddPaTrapporSaveExtraObject);
                breddPaTrapporShowExtraObjects = findViewById(R.id.breddPaTrapporShowExtraObjects);
                breddPaTrapporTrash = findViewById(R.id.breddPaTrapporTrash);

                breddPaTrapporSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (breddPaTrapporExtraName.getText().toString().trim().length() > 0 &&
                                breddPaTrapporExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getBreddPaTrapporExtraObjects();

                            showExtraObjects += String.valueOf(breddPaTrapporExtraName.getText()) + "      " +
                                    String.valueOf(breddPaTrapporExtraStatus.getText()) + "\n";

                            propertyList.setBreddPaTrapporExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            breddPaTrapporExtraName.setText("");
                            breddPaTrapporExtraStatus.setText("");

                            breddPaTrapporShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                breddPaTrapporTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("4_2");

                    }
                });

                visuelMarkeringPaForstaOchSistaStegetExtraName = findViewById(R.id.visuelMarkeringPaForstaOchSistaStegetExtraName);
                visuelMarkeringPaForstaOchSistaStegetExtraStatus = findViewById(R.id.visuelMarkeringPaForstaOchSistaStegetExtraStatus);
                visuelMarkeringPaForstaOchSistaStegetSaveExtraObject = findViewById(R.id.visuelMarkeringPaForstaOchSistaStegetSaveExtraObject);
                visuelMarkeringPaForstaOchSistaStegetShowExtraObjects = findViewById(R.id.visuelMarkeringPaForstaOchSistaStegetShowExtraObjects);
                visuelMarkeringPaForstaOchSistaStegetTrash = findViewById(R.id.visuelMarkeringPaForstaOchSistaStegetTrash);

                visuelMarkeringPaForstaOchSistaStegetSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (visuelMarkeringPaForstaOchSistaStegetExtraName.getText().toString().trim().length() > 0 &&
                                visuelMarkeringPaForstaOchSistaStegetExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getVisuelMarkeringPaForstaOchSistaStegetExtraObjects();

                            showExtraObjects += String.valueOf(visuelMarkeringPaForstaOchSistaStegetExtraName.getText()) + "      " +
                                    String.valueOf(visuelMarkeringPaForstaOchSistaStegetExtraStatus.getText()) + "\n";

                            propertyList.setVisuelMarkeringPaForstaOchSistaStegetExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            visuelMarkeringPaForstaOchSistaStegetExtraName.setText("");
                            visuelMarkeringPaForstaOchSistaStegetExtraStatus.setText("");

                            visuelMarkeringPaForstaOchSistaStegetShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                visuelMarkeringPaForstaOchSistaStegetTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("4_3");

                    }
                });

                taktilVarningForeForstaUppgaendeTrappstegExtraName = findViewById(R.id.taktilVarningForeForstaUppgaendeTrappstegExtraName);
                taktilVarningForeForstaUppgaendeTrappstegExtraStatus = findViewById(R.id.taktilVarningForeForstaUppgaendeTrappstegExtraStatus);
                taktilVarningForeForstaUppgaendeTrappstegSaveExtraObject = findViewById(R.id.taktilVarningForeForstaUppgaendeTrappstegSaveExtraObject);
                taktilVarningForeForstaUppgaendeTrappstegShowExtraObjects = findViewById(R.id.taktilVarningForeForstaUppgaendeTrappstegShowExtraObjects);
                taktilVarningForeForstaUppgaendeTrappstegTrash = findViewById(R.id.taktilVarningForeForstaUppgaendeTrappstegTrash);

                taktilVarningForeForstaUppgaendeTrappstegSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (taktilVarningForeForstaUppgaendeTrappstegExtraName.getText().toString().trim().length() > 0 &&
                                taktilVarningForeForstaUppgaendeTrappstegExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getTaktilVarningForeForstaUppgaendeTrappstegExtraObjects();

                            showExtraObjects += String.valueOf(taktilVarningForeForstaUppgaendeTrappstegExtraName.getText()) + "      " +
                                    String.valueOf(taktilVarningForeForstaUppgaendeTrappstegExtraStatus.getText()) + "\n";

                            propertyList.setTaktilVarningForeForstaUppgaendeTrappstegExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            taktilVarningForeForstaUppgaendeTrappstegExtraName.setText("");
                            taktilVarningForeForstaUppgaendeTrappstegExtraStatus.setText("");

                            taktilVarningForeForstaUppgaendeTrappstegShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                taktilVarningForeForstaUppgaendeTrappstegTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("4_4");

                    }
                });

                ramperForPersonerMedFunktionsnedsättningarExtraName = findViewById(R.id.ramperForPersonerMedFunktionsnedsättningarExtraName);
                ramperForPersonerMedFunktionsnedsättningarExtraStatus = findViewById(R.id.ramperForPersonerMedFunktionsnedsättningarExtraStatus);
                ramperForPersonerMedFunktionsnedsättningarSaveExtraObject = findViewById(R.id.ramperForPersonerMedFunktionsnedsättningarSaveExtraObject);
                ramperForPersonerMedFunktionsnedsättningarShowExtraObjects = findViewById(R.id.ramperForPersonerMedFunktionsnedsättningarShowExtraObjects);
                ramperForPersonerMedFunktionsnedsättningarTrash = findViewById(R.id.ramperForPersonerMedFunktionsnedsättningarTrash);

                ramperForPersonerMedFunktionsnedsättningarSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ramperForPersonerMedFunktionsnedsättningarExtraName.getText().toString().trim().length() > 0 &&
                                ramperForPersonerMedFunktionsnedsättningarExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getRamperForPersonerMedFunktionsnedsattningarExtraObjects();

                            showExtraObjects += String.valueOf(ramperForPersonerMedFunktionsnedsättningarExtraName.getText()) + "      " +
                                    String.valueOf(ramperForPersonerMedFunktionsnedsättningarExtraStatus.getText()) + "\n";

                            propertyList.setRamperForPersonerMedFunktionsnedsattningarExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            ramperForPersonerMedFunktionsnedsättningarExtraName.setText("");
                            ramperForPersonerMedFunktionsnedsättningarExtraStatus.setText("");

                            ramperForPersonerMedFunktionsnedsättningarShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                ramperForPersonerMedFunktionsnedsättningarTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("4_5");

                    }
                });

                ledstangerPaBadaSidorOchTvaNivaerExtraName = findViewById(R.id.ledstangerPaBadaSidorOchTvaNivaerExtraName);
                ledstangerPaBadaSidorOchTvaNivaerExtraStatus = findViewById(R.id.ledstangerPaBadaSidorOchTvaNivaerExtraStatus);
                ledstangerPaBadaSidorOchTvaNivaerSaveExtraObject = findViewById(R.id.ledstangerPaBadaSidorOchTvaNivaerSaveExtraObject);
                ledstangerPaBadaSidorOchTvaNivaerShowExtraObjects = findViewById(R.id.ledstangerPaBadaSidorOchTvaNivaerShowExtraObjects);
                ledstangerPaBadaSidorOchTvaNivaerTrash = findViewById(R.id.ledstangerPaBadaSidorOchTvaNivaerTrash);

                ledstangerPaBadaSidorOchTvaNivaerSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ledstangerPaBadaSidorOchTvaNivaerExtraName.getText().toString().trim().length() > 0 &&
                                ledstangerPaBadaSidorOchTvaNivaerExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getLedstangerPaBadaSidorOchTvaNivaerExtraObjects();

                            showExtraObjects += String.valueOf(ledstangerPaBadaSidorOchTvaNivaerExtraName.getText()) + "      " +
                                    String.valueOf(ledstangerPaBadaSidorOchTvaNivaerExtraStatus.getText()) + "\n";

                            propertyList.setLedstangerPaBadaSidorOchTvaNivaerExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            ledstangerPaBadaSidorOchTvaNivaerExtraName.setText("");
                            ledstangerPaBadaSidorOchTvaNivaerExtraStatus.setText("");

                            ledstangerPaBadaSidorOchTvaNivaerShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                ledstangerPaBadaSidorOchTvaNivaerTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("4_6");

                    }
                });

                hissarExtraName = findViewById(R.id.hissarExtraName);
                hissarExtraStatus = findViewById(R.id.hissarExtraStatus);
                hissarSaveExtraObject = findViewById(R.id.hissarSaveExtraObject);
                hissarShowExtraObjects = findViewById(R.id.hissarShowExtraObject);
                hissarTrash = findViewById(R.id.hissarTrash);

                hissarSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (hissarExtraName.getText().toString().trim().length() > 0 &&
                                hissarExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getHissarExtraObjects();

                            showExtraObjects += String.valueOf(hissarExtraName.getText()) + "      " +
                                    String.valueOf(hissarExtraStatus.getText()) + "\n";

                            propertyList.setHissarExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            hissarExtraName.setText("");
                            hissarExtraStatus.setText("");

                            hissarShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                hissarTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("4_7");

                    }
                });

                rulltrapporOchRullramperExtraName = findViewById(R.id.rulltrapporOchRullramperExtraName);
                rulltrapporOchRullramperExtraStatus = findViewById(R.id.rulltrapporOchRullramperExtraStatus);
                rulltrapporOchRullramperSaveExtraObject = findViewById(R.id.rulltrapporOchRullramperSaveExtraObject);
                rulltrapporOchRullramperShowExtraObjects = findViewById(R.id.rulltrapporOchRullramperShowExtraObjects);
                rulltrapporOchRullramperTrash = findViewById(R.id.rulltrapporOchRullramperTrash);

                rulltrapporOchRullramperSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (rulltrapporOchRullramperExtraName.getText().toString().trim().length() > 0 &&
                                rulltrapporOchRullramperExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getRulltrapporOchRullramperExtraObjects();

                            showExtraObjects += String.valueOf(rulltrapporOchRullramperExtraName.getText()) + "      " +
                                    String.valueOf(rulltrapporOchRullramperExtraStatus.getText()) + "\n";

                            propertyList.setRulltrapporOchRullramperExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            rulltrapporOchRullramperExtraName.setText("");
                            rulltrapporOchRullramperExtraStatus.setText("");

                            rulltrapporOchRullramperShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                rulltrapporOchRullramperTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("4_8");

                    }
                });

                plankorsningarExtraName = findViewById(R.id.plankorsningarExtraName);
                plankorsningarExtraStatus = findViewById(R.id.plankorsningarExtraStatus);
                plankorsningarSaveExtraObject = findViewById(R.id.plankorsningarSaveExtraObject);
                plankorsningarShowExtraObjects = findViewById(R.id.plankorsningarShowExtraObjects);
                plankorsningarTrash = findViewById(R.id.plankorsningarTrash);

                plankorsningarSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (plankorsningarExtraName.getText().toString().trim().length() > 0 &&
                                plankorsningarExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getPlankorsningarExtraObjects();

                            showExtraObjects += String.valueOf(plankorsningarExtraName.getText()) + "      " +
                                    String.valueOf(plankorsningarExtraStatus.getText()) + "\n";

                            propertyList.setPlankorsningarExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            plankorsningarExtraName.setText("");
                            plankorsningarExtraStatus.setText("");

                            plankorsningarShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                plankorsningarTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("4_9");

                    }
                });

                break;
            case 5:
                setContentView(R.layout.property_list_gangvagsmarkering);
                tydligMarkering = findViewById(R.id.tydligMarkering);
                tillhandahållandeAvInformationTillSynskadade = findViewById(R.id.tillhandahållandeAvInformationTillSynskadade);
                fjarrstyrdaLjudanordningarEllerTeleapplikationer = findViewById(R.id.fjarrstyrdaLjudanordningarEllerTeleapplikationer);
                taktilInformationPaLedstangerEllerVaggar = findViewById(R.id.taktilInformationPaLedstangerEllerVaggar);

                tydligMarkeringExtraName = findViewById(R.id.tydligMarkeringExtraName);
                tydligMarkeringExtraStatus = findViewById(R.id.tydligMarkeringExtraStatus);
                tydligMarkeringSaveExtraObject = findViewById(R.id.tydligMarkeringSaveExtraObject);
                tydligMarkeringShowExtraObjects = findViewById(R.id.tydligMarkeringShowExtraObjects);
                tydligMarkeringTrash = findViewById(R.id.tydligMarkeringTrash);

                tydligMarkeringSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (tydligMarkeringExtraName.getText().toString().trim().length() > 0 &&
                                tydligMarkeringExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getTydligMarkeringExtraObjects();

                            showExtraObjects += String.valueOf(tydligMarkeringExtraName.getText()) + "      " +
                                    String.valueOf(tydligMarkeringExtraStatus.getText()) + "\n";

                            propertyList.setTydligMarkeringExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            tydligMarkeringExtraName.setText("");
                            tydligMarkeringExtraStatus.setText("");

                            tydligMarkeringShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                tydligMarkeringTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("5_1");

                    }
                });

                tillhandahållandeAvInformationTillSynskadadeExtraName = findViewById(R.id.tillhandahållandeAvInformationTillSynskadadeExtraName);
                tillhandahållandeAvInformationTillSynskadadeExtraStatus = findViewById(R.id.tillhandahållandeAvInformationTillSynskadadeExtraStatus);
                tillhandahållandeAvInformationTillSynskadadeSaveExtraObject = findViewById(R.id.tillhandahållandeAvInformationTillSynskadadeSaveExtraObject);
                tillhandahållandeAvInformationTillSynskadadeShowExtraObjects = findViewById(R.id.tillhandahållandeAvInformationTillSynskadadeShowExtraObjects);
                tillhandahållandeAvInformationTillSynskadadeTrash = findViewById(R.id.tillhandahållandeAvInformationTillSynskadadeTrash);

                tillhandahållandeAvInformationTillSynskadadeSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (tillhandahållandeAvInformationTillSynskadadeExtraName.getText().toString().trim().length() > 0 &&
                                tillhandahållandeAvInformationTillSynskadadeExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getTillhandahållandeAvInformationTillSynskadadeExtraObjects();

                            showExtraObjects += String.valueOf(tillhandahållandeAvInformationTillSynskadadeExtraName.getText()) + "      " +
                                    String.valueOf(tillhandahållandeAvInformationTillSynskadadeExtraStatus.getText()) + "\n";

                            propertyList.setTillhandahållandeAvInformationTillSynskadadeExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            tillhandahållandeAvInformationTillSynskadadeExtraName.setText("");
                            tillhandahållandeAvInformationTillSynskadadeExtraStatus.setText("");

                            tillhandahållandeAvInformationTillSynskadadeShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                tillhandahållandeAvInformationTillSynskadadeTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("5_2");

                    }
                });

                fjarrstyrdaLjudanordningarEllerTeleapplikationerExtraName = findViewById(R.id.fjarrstyrdaLjudanordningarEllerTeleapplikationerExtraName);
                fjarrstyrdaLjudanordningarEllerTeleapplikationerExtraStatus = findViewById(R.id.fjarrstyrdaLjudanordningarEllerTeleapplikationerExtraStatus);
                fjarrstyrdaLjudanordningarEllerTeleapplikationerSaveExtraObject = findViewById(R.id.fjarrstyrdaLjudanordningarEllerTeleapplikationerSaveExtraObject);
                fjarrstyrdaLjudanordningarEllerTeleapplikationerShowExtraObjects = findViewById(R.id.fjarrstyrdaLjudanordningarEllerTeleapplikationerShowExtraObjects);
                fjarrstyrdaLjudanordningarEllerTeleapplikationerTrash = findViewById(R.id.fjarrstyrdaLjudanordningarEllerTeleapplikationerTrash);

                fjarrstyrdaLjudanordningarEllerTeleapplikationerSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (fjarrstyrdaLjudanordningarEllerTeleapplikationerExtraName.getText().toString().trim().length() > 0 &&
                                fjarrstyrdaLjudanordningarEllerTeleapplikationerExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getFjarrstyrdaLjudanordningarEllerTeleapplikationerExtraObjects();

                            showExtraObjects += String.valueOf(fjarrstyrdaLjudanordningarEllerTeleapplikationerExtraName.getText()) + "      " +
                                    String.valueOf(fjarrstyrdaLjudanordningarEllerTeleapplikationerExtraStatus.getText()) + "\n";

                            propertyList.setFjarrstyrdaLjudanordningarEllerTeleapplikationerExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            fjarrstyrdaLjudanordningarEllerTeleapplikationerExtraName.setText("");
                            fjarrstyrdaLjudanordningarEllerTeleapplikationerExtraStatus.setText("");

                            fjarrstyrdaLjudanordningarEllerTeleapplikationerShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                fjarrstyrdaLjudanordningarEllerTeleapplikationerTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("5_3");

                    }
                });

                taktilInformationPaLedstangerEllerVaggarExtraName = findViewById(R.id.taktilInformationPaLedstangerEllerVaggarExtraName);
                taktilInformationPaLedstangerEllerVaggarExtraStatus = findViewById(R.id.taktilInformationPaLedstangerEllerVaggarExtraStatus);
                taktilInformationPaLedstangerEllerVaggarSaveExtraObject = findViewById(R.id.taktilInformationPaLedstangerEllerVaggarSaveExtraObject);
                taktilInformationPaLedstangerEllerVaggarShowExtraObjects = findViewById(R.id.taktilInformationPaLedstangerEllerVaggarShowExtraObjects);
                taktilInformationPaLedstangerEllerVaggarTrash = findViewById(R.id.taktilInformationPaLedstangerEllerVaggarTrash);

                taktilInformationPaLedstangerEllerVaggarSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (taktilInformationPaLedstangerEllerVaggarExtraName.getText().toString().trim().length() > 0 &&
                                taktilInformationPaLedstangerEllerVaggarExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getTaktilInformationPaLedstangerEllerVaggarExtraObjects();

                            showExtraObjects += String.valueOf(taktilInformationPaLedstangerEllerVaggarExtraName.getText()) + "      " +
                                    String.valueOf(taktilInformationPaLedstangerEllerVaggarExtraStatus.getText()) + "\n";

                            propertyList.setTaktilInformationPaLedstangerEllerVaggarExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            taktilInformationPaLedstangerEllerVaggarExtraName.setText("");
                            taktilInformationPaLedstangerEllerVaggarExtraStatus.setText("");

                            taktilInformationPaLedstangerEllerVaggarShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                taktilInformationPaLedstangerEllerVaggarTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("5_4");

                    }
                });

                break;
            case 6:
                setContentView(R.layout.property_list_golvytor);
                halksakerhet = findViewById(R.id.halksakerhet);
                ojamnheterSomOverstiger = findViewById(R.id.ojamnheterSomOverstiger);

                halksakerhetExtraName = findViewById(R.id.halksakerhetExtraName);
                halksakerhetExtraStatus = findViewById(R.id.halksakerhetExtraStatus);
                halksakerhetSaveExtraObject = findViewById(R.id.halksakerhetSaveExtraObject);
                halksakerhetShowExtraObjects = findViewById(R.id.halksakerhetShowExtraObject);
                halksakerhetTrash = findViewById(R.id.halksakerhetTrash);

                halksakerhetSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (halksakerhetExtraName.getText().toString().trim().length() > 0 &&
                                halksakerhetExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getHalksakerhetExtraObjects();

                            showExtraObjects += String.valueOf(halksakerhetExtraName.getText()) + "      " +
                                    String.valueOf(halksakerhetExtraStatus.getText()) + "\n";

                            propertyList.setHalksakerhetExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            halksakerhetExtraName.setText("");
                            halksakerhetExtraStatus.setText("");

                            halksakerhetShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                halksakerhetTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("6_1");

                    }
                });

                ojamnheterSomOverstigerExtraName = findViewById(R.id.ojamnheterSomOverstigerExtraName);
                ojamnheterSomOverstigerExtraStatus = findViewById(R.id.ojamnheterSomOverstigerExtraStatus);
                ojamnheterSomOverstigerSaveExtraObject = findViewById(R.id.ojamnheterSomOverstigerSaveExtraObject);
                ojamnheterSomOverstigerShowExtraObjects = findViewById(R.id.ojamnheterSomOverstigerShowExtraObjects);
                ojamnheterSomOverstigerTrash = findViewById(R.id.ojamnheterSomOverstigerTrash);

                ojamnheterSomOverstigerSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ojamnheterSomOverstigerExtraName.getText().toString().trim().length() > 0 &&
                                ojamnheterSomOverstigerExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getOjamnheterSomOverstigerExtraObjects();

                            showExtraObjects += String.valueOf(ojamnheterSomOverstigerExtraName.getText()) + "      " +
                                    String.valueOf(ojamnheterSomOverstigerExtraStatus.getText()) + "\n";

                            propertyList.setOjamnheterSomOverstigerExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            ojamnheterSomOverstigerExtraName.setText("");
                            ojamnheterSomOverstigerExtraStatus.setText("");

                            ojamnheterSomOverstigerShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                ojamnheterSomOverstigerTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("6_2");

                    }
                });

                break;
            case 7:
                setContentView(R.layout.property_list_markering_av_genomskinliga_hinder);
                glasdorrarEllerGenomskinligaVaggarLangsGangvagar = findViewById(R.id.glasdorrarEllerGenomskinligaVaggarLangsGangvagar);

                glasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraName = findViewById(R.id.glasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraName);
                glasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraStatus = findViewById(R.id.glasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraStatus);
                glasdorrarEllerGenomskinligaVaggarLangsGangvagarSaveExtraObject = findViewById(R.id.glasdorrarEllerGenomskinligaVaggarLangsGangvagarSaveExtraObject);
                glasdorrarEllerGenomskinligaVaggarLangsGangvagarShowExtraObjects = findViewById(R.id.glasdorrarEllerGenomskinligaVaggarLangsGangvagarShowExtraObjects);
                glasdorrarEllerGenomskinligaVaggarLangsGangvagarTrash = findViewById(R.id.glasdorrarEllerGenomskinligaVaggarLangsGangvagarTrash);

                glasdorrarEllerGenomskinligaVaggarLangsGangvagarSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (glasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraName.getText().toString().trim().length() > 0 &&
                                glasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getGlasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraObjects();

                            showExtraObjects += String.valueOf(glasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraName.getText()) + "      " +
                                    String.valueOf(glasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraStatus.getText()) + "\n";

                            propertyList.setGlasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            glasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraName.setText("");
                            glasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraStatus.setText("");

                            glasdorrarEllerGenomskinligaVaggarLangsGangvagarShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                glasdorrarEllerGenomskinligaVaggarLangsGangvagarTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("7_1");

                    }
                });

                break;
            case 8:
                setContentView(R.layout.property_layout_toaletter_och_skotsplatser);
                toalettutrymmeAnpassatForRullstolsburnaPersoner = findViewById(R.id.toalettutrymmeAnpassatForRullstolsburnaPersoner);
                skotplatserTillgangligaForBadeKonen = findViewById(R.id.skotplatserTillgangligaForBadeKonen);

                toalettutrymmeAnpassatForRullstolsburnaPersonerExtraName = findViewById(R.id.toalettutrymmeAnpassatForRullstolsburnaPersonerExtraName);
                toalettutrymmeAnpassatForRullstolsburnaPersonerExtraStatus = findViewById(R.id.toalettutrymmeAnpassatForRullstolsburnaPersonerExtraStatus);
                toalettutrymmeAnpassatForRullstolsburnaPersonerSaveExtraObject = findViewById(R.id.toalettutrymmeAnpassatForRullstolsburnaPersonerSaveExtraObject);
                toalettutrymmeAnpassatForRullstolsburnaPersonerShowExtraObjects = findViewById(R.id.toalettutrymmeAnpassatForRullstolsburnaPersonerShowExtraObjects);
                toalettutrymmeAnpassatForRullstolsburnaPersonerTrash = findViewById(R.id.toalettutrymmeAnpassatForRullstolsburnaPersonerTrash);

                toalettutrymmeAnpassatForRullstolsburnaPersonerSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (toalettutrymmeAnpassatForRullstolsburnaPersonerExtraName.getText().toString().trim().length() > 0 &&
                                toalettutrymmeAnpassatForRullstolsburnaPersonerExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getToalettutrymmeAnpassatForRullstolsburnaPersonerExtraObjects();

                            showExtraObjects += String.valueOf(toalettutrymmeAnpassatForRullstolsburnaPersonerExtraName.getText()) + "      " +
                                    String.valueOf(toalettutrymmeAnpassatForRullstolsburnaPersonerExtraStatus.getText()) + "\n";

                            propertyList.setToalettutrymmeAnpassatForRullstolsburnaPersonerExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            toalettutrymmeAnpassatForRullstolsburnaPersonerExtraName.setText("");
                            toalettutrymmeAnpassatForRullstolsburnaPersonerExtraStatus.setText("");

                            toalettutrymmeAnpassatForRullstolsburnaPersonerShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                toalettutrymmeAnpassatForRullstolsburnaPersonerTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("8_1");

                    }
                });

                skotplatserTillgangligaForBadeKonenExtraName = findViewById(R.id.skotplatserTillgangligaForBadeKonenExtraName);
                skotplatserTillgangligaForBadeKonenExtraStatus = findViewById(R.id.skotplatserTillgangligaForBadeKonenExtraStatus);
                skotplatserTillgangligaForBadeKonenSaveExtraObject = findViewById(R.id.skotplatserTillgangligaForBadeKonenSaveExtraObject);
                skotplatserTillgangligaForBadeKonenShowExtraObjects = findViewById(R.id.skotplatserTillgangligaForBadeKonenShowExtraObjects);
                skotplatserTillgangligaForBadeKonenTrash = findViewById(R.id.skotplatserTillgangligaForBadeKonenTrash);

                skotplatserTillgangligaForBadeKonenSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (skotplatserTillgangligaForBadeKonenExtraName.getText().toString().trim().length() > 0 &&
                                skotplatserTillgangligaForBadeKonenExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getSkotplatserTillgangligaForBadeKonenExtraObjects();

                            showExtraObjects += String.valueOf(skotplatserTillgangligaForBadeKonenExtraName.getText()) + "      " +
                                    String.valueOf(skotplatserTillgangligaForBadeKonenExtraStatus.getText()) + "\n";

                            propertyList.setSkotplatserTillgangligaForBadeKonenExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            skotplatserTillgangligaForBadeKonenExtraName.setText("");
                            skotplatserTillgangligaForBadeKonenExtraStatus.setText("");

                            skotplatserTillgangligaForBadeKonenShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                skotplatserTillgangligaForBadeKonenTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("8_2");

                    }
                });

                break;
            case 9:
                setContentView(R.layout.property_layout_inredning_och_fristaende_enheter);
                kontrastMotBakgrundOchAvrundandeKanter = findViewById(R.id.kontrastMotBakgrundOchAvrundandeKanter);
                placeringAvInredningOchEnheter = findViewById(R.id.placeringAvInredningOchEnheter);
                sittmojligheterOchPlatsForEnRullstolsbundenPerson = findViewById(R.id.sittmojligheterOchPlatsForEnRullstolsbundenPerson);
                vaderskyddatOmrade = findViewById(R.id.vaderskyddatOmrade);

                kontrastMotBakgrundOchAvrundandeKanterExtraName = findViewById(R.id.kontrastMotBakgrundOchAvrundandeKanterExtraName);
                kontrastMotBakgrundOchAvrundandeKanterExtraStatus = findViewById(R.id.kontrastMotBakgrundOchAvrundandeKanterExtraStatus);
                kontrastMotBakgrundOchAvrundandeKanterSaveExtraObject = findViewById(R.id.kontrastMotBakgrundOchAvrundandeKanterSaveExtraObject);
                kontrastMotBakgrundOchAvrundandeKanterShowExtraObjects = findViewById(R.id.kontrastMotBakgrundOchAvrundandeKanterShowExtraObjects);
                kontrastMotBakgrundOchAvrundandeKanterTrash = findViewById(R.id.kontrastMotBakgrundOchAvrundandeKanterTrash);

                kontrastMotBakgrundOchAvrundandeKanterSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (kontrastMotBakgrundOchAvrundandeKanterExtraName.getText().toString().trim().length() > 0 &&
                                kontrastMotBakgrundOchAvrundandeKanterExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getKontrastMotBakgrundOchAvrundandeKanterExtraObjects();

                            showExtraObjects += String.valueOf(kontrastMotBakgrundOchAvrundandeKanterExtraName.getText()) + "      " +
                                    String.valueOf(kontrastMotBakgrundOchAvrundandeKanterExtraStatus.getText()) + "\n";

                            propertyList.setKontrastMotBakgrundOchAvrundandeKanterExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            kontrastMotBakgrundOchAvrundandeKanterExtraName.setText("");
                            kontrastMotBakgrundOchAvrundandeKanterExtraStatus.setText("");

                            kontrastMotBakgrundOchAvrundandeKanterShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                kontrastMotBakgrundOchAvrundandeKanterTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("9_1");

                    }
                });

                placeringAvInredningOchEnheterExtraName = findViewById(R.id.placeringAvInredningOchEnheterExtraName);
                placeringAvInredningOchEnheterExtraStatus = findViewById(R.id.placeringAvInredningOchEnheterExtraStatus);
                placeringAvInredningOchEnheterSaveExtraObject = findViewById(R.id.placeringAvInredningOchEnheterSaveExtraObject);
                placeringAvInredningOchEnheterShowExtraObjects = findViewById(R.id.placeringAvInredningOchEnheterShowExtraObjects);
                placeringAvInredningOchEnheterTrash = findViewById(R.id.placeringAvInredningOchEnheterTrash);

                placeringAvInredningOchEnheterSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (placeringAvInredningOchEnheterExtraName.getText().toString().trim().length() > 0 &&
                                placeringAvInredningOchEnheterExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getPlaceringAvInredningOchEnheterExtraObjects();

                            showExtraObjects += String.valueOf(placeringAvInredningOchEnheterExtraName.getText()) + "      " +
                                    String.valueOf(placeringAvInredningOchEnheterExtraStatus.getText()) + "\n";

                            propertyList.setPlaceringAvInredningOchEnheterExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            placeringAvInredningOchEnheterExtraName.setText("");
                            placeringAvInredningOchEnheterExtraStatus.setText("");

                            placeringAvInredningOchEnheterShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                placeringAvInredningOchEnheterTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("9_2");

                    }
                });

                sittmojligheterOchPlatsForEnRullstolsbundenPersonExtraName = findViewById(R.id.sittmojligheterOchPlatsForEnRullstolsbundenPersonExtraName);
                sittmojligheterOchPlatsForEnRullstolsbundenPersonExtraStatus = findViewById(R.id.sittmojligheterOchPlatsForEnRullstolsbundenPersonExtraStatus);
                sittmojligheterOchPlatsForEnRullstolsbundenPersonSaveExtraObject = findViewById(R.id.sittmojligheterOchPlatsForEnRullstolsbundenPersonSaveExtraObject);
                sittmojligheterOchPlatsForEnRullstolsbundenPersonShowExtraObjects = findViewById(R.id.sittmojligheterOchPlatsForEnRullstolsbundenPersonShowExtraObjects);
                sittmojligheterOchPlatsForEnRullstolsbundenPersonTrash = findViewById(R.id.sittmojligheterOchPlatsForEnRullstolsbundenPersonTrash);

                sittmojligheterOchPlatsForEnRullstolsbundenPersonSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (sittmojligheterOchPlatsForEnRullstolsbundenPersonExtraName.getText().toString().trim().length() > 0 &&
                                sittmojligheterOchPlatsForEnRullstolsbundenPersonExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getSittmojligheterOchPlatsForEnRullstolsbundenPersonExtraObjects();

                            showExtraObjects += String.valueOf(sittmojligheterOchPlatsForEnRullstolsbundenPersonExtraName.getText()) + "      " +
                                    String.valueOf(sittmojligheterOchPlatsForEnRullstolsbundenPersonExtraStatus.getText()) + "\n";

                            propertyList.setSittmojligheterOchPlatsForEnRullstolsbundenPersonExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            sittmojligheterOchPlatsForEnRullstolsbundenPersonExtraName.setText("");
                            sittmojligheterOchPlatsForEnRullstolsbundenPersonExtraStatus.setText("");

                            sittmojligheterOchPlatsForEnRullstolsbundenPersonShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                sittmojligheterOchPlatsForEnRullstolsbundenPersonTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("9_3");

                    }
                });

                vaderskyddatOmradeExtraName = findViewById(R.id.vaderskyddatOmradeExtraName);
                vaderskyddatOmradeExtraStatus = findViewById(R.id.vaderskyddatOmradeExtraStatus);
                vaderskyddatOmradeSaveExtraObject = findViewById(R.id.vaderskyddatOmradeSaveExtraObject);
                vaderskyddatOmradeShowExtraObjects = findViewById(R.id.vaderskyddatOmradeShowExtraObjects);
                vaderskyddatOmradeTrash = findViewById(R.id.vaderskyddatOmradeTrash);

                vaderskyddatOmradeSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (vaderskyddatOmradeExtraName.getText().toString().trim().length() > 0 &&
                                vaderskyddatOmradeExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getVaderskyddatOmradeExtraObjects();

                            showExtraObjects += String.valueOf(vaderskyddatOmradeExtraName.getText()) + "      " +
                                    String.valueOf(vaderskyddatOmradeExtraStatus.getText()) + "\n";

                            propertyList.setVaderskyddatOmradeExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            vaderskyddatOmradeExtraName.setText("");
                            vaderskyddatOmradeExtraStatus.setText("");

                            vaderskyddatOmradeShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                vaderskyddatOmradeTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("9_4");

                    }
                });

                break;
            case 10:
                setContentView(R.layout.property_list_belysning);
                belysningPaStationensExternaOmraden = findViewById(R.id.belysningPaStationensExternaOmraden);
                belysningLangsHinderfriaGangvagar = findViewById(R.id.belysningLangsHinderfriaGangvagar);
                belysningPaPlattformar = findViewById(R.id.belysningPaPlattformar);
                nodbelysning = findViewById(R.id.nodbelysning);

                belysningPaStationensExternaOmradenExtraName = findViewById(R.id.belysningPaStationensExternaOmradenExtraName);
                belysningPaStationensExternaOmradenExtraStatus = findViewById(R.id.belysningPaStationensExternaOmradenExtraStatus);
                belysningPaStationensExternaOmradenSaveExtraObject = findViewById(R.id.belysningPaStationensExternaOmradenSaveExtraObject);
                belysningPaStationensExternaOmradenShowExtraObjects = findViewById(R.id.belysningPaStationensExternaOmradenShowExtraObjects);
                belysningPaStationensExternaOmradenTrash = findViewById(R.id.belysningPaStationensExternaOmradenTrash);

                belysningPaStationensExternaOmradenSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (belysningPaStationensExternaOmradenExtraName.getText().toString().trim().length() > 0 &&
                                belysningPaStationensExternaOmradenExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getBelysningPaStationensExternaOmradenExtraObjects();

                            showExtraObjects += String.valueOf(belysningPaStationensExternaOmradenExtraName.getText()) + "      " +
                                    String.valueOf(belysningPaStationensExternaOmradenExtraStatus.getText()) + "\n";

                            propertyList.setBelysningPaStationensExternaOmradenExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            belysningPaStationensExternaOmradenExtraName.setText("");
                            belysningPaStationensExternaOmradenExtraStatus.setText("");

                            belysningPaStationensExternaOmradenShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                belysningPaStationensExternaOmradenTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("10_1");

                    }
                });

                belysningLangsHinderfriaGangvagarExtraName = findViewById(R.id.belysningLangsHinderfriaGangvagarExtraName);
                belysningLangsHinderfriaGangvagarExtraStatus = findViewById(R.id.belysningLangsHinderfriaGangvagarExtraStatus);
                belysningLangsHinderfriaGangvagarSaveExtraObject = findViewById(R.id.belysningLangsHinderfriaGangvagarSaveExtraObject);
                belysningLangsHinderfriaGangvagarShowExtraObjects = findViewById(R.id.belysningLangsHinderfriaGangvagarShowExtraObjects);
                belysningLangsHinderfriaGangvagarTrash = findViewById(R.id.belysningLangsHinderfriaGangvagarTrash);

                belysningLangsHinderfriaGangvagarSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (belysningLangsHinderfriaGangvagarExtraName.getText().toString().trim().length() > 0 &&
                                belysningLangsHinderfriaGangvagarExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getBelysningLangsHinderfriaGangvagarExtraObjects();

                            showExtraObjects += String.valueOf(belysningLangsHinderfriaGangvagarExtraName.getText()) + "      " +
                                    String.valueOf(belysningLangsHinderfriaGangvagarExtraStatus.getText()) + "\n";

                            propertyList.setBelysningLangsHinderfriaGangvagarExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            belysningLangsHinderfriaGangvagarExtraName.setText("");
                            belysningLangsHinderfriaGangvagarExtraStatus.setText("");

                            belysningLangsHinderfriaGangvagarShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                belysningLangsHinderfriaGangvagarTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("10_2");

                    }
                });

                belysningPaPlattformarExtraName = findViewById(R.id.belysningPaPlattformarExtraName);
                belysningPaPlattformarExtraStatus = findViewById(R.id.belysningPaPlattformarExtraStatus);
                belysningPaPlattformarSaveExtraObject = findViewById(R.id.belysningPaPlattformarSaveExtraObject);
                belysningPaPlattformarShowExtraObjects = findViewById(R.id.belysningPaPlattformarShowExtraObjects);
                belysningPaPlattformarTrash = findViewById(R.id.belysningPaPlattformarTrash);

                belysningPaPlattformarSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (belysningPaPlattformarExtraName.getText().toString().trim().length() > 0 &&
                                belysningPaPlattformarExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getBelysningPaPlattformarExtraObjects();

                            showExtraObjects += String.valueOf(belysningPaPlattformarExtraName.getText()) + "      " +
                                    String.valueOf(belysningPaPlattformarExtraStatus.getText()) + "\n";

                            propertyList.setBelysningPaPlattformarExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            belysningPaPlattformarExtraName.setText("");
                            belysningPaPlattformarExtraStatus.setText("");

                            belysningPaPlattformarShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                belysningPaPlattformarTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("10_3");

                    }
                });

                nodbelysningExtraName = findViewById(R.id.nodbelysningExtraName);
                nodbelysningExtraStatus = findViewById(R.id.nodbelysningExtraStatus);
                nodbelysningSaveExtraObject = findViewById(R.id.nodbelysningSaveExtraObject);
                nodbelysningShowExtraObjects = findViewById(R.id.nodbelysningShowExtraObjects);
                nodbelysningTrash = findViewById(R.id.nodbelysningTrash);

                nodbelysningSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (nodbelysningExtraName.getText().toString().trim().length() > 0 &&
                                nodbelysningExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getNodbelysningExtraObjects();

                            showExtraObjects += String.valueOf(nodbelysningExtraName.getText()) + "      " +
                                    String.valueOf(nodbelysningExtraStatus.getText()) + "\n";

                            propertyList.setNodbelysningExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            nodbelysningExtraName.setText("");
                            nodbelysningExtraStatus.setText("");

                            nodbelysningShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                nodbelysningTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("10_4");

                    }
                });

                break;
            case 11:
                setContentView(R.layout.property_list_visuell_information);
                skyltarAvstand = findViewById(R.id.skyltarAvstand);
                pictogram = findViewById(R.id.pictogram);
                kontrast = findViewById(R.id.kontrast);
                enhetlig = findViewById(R.id.enhetlig);
                synligIAllaBelysningsforhallanden = findViewById(R.id.synligIAllaBelysningsforhallanden);
                skyltarEnligtISO = findViewById(R.id.skyltarEnligtISO);

                skyltarAvstandExtraName = findViewById(R.id.skyltarAvstandExtraName);
                skyltarAvstandExtraStatus = findViewById(R.id.skyltarAvstandExtraStatus);
                skyltarAvstandSaveExtraObject = findViewById(R.id.skyltarAvstandSaveExtraObjects);
                skyltarAvstandShowExtraObjects = findViewById(R.id.skyltarAvstandShowExtraObjects);
                skyltarAvstandTrash = findViewById(R.id.skyltarAvstandTrash);

                skyltarAvstandSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (skyltarAvstandExtraName.getText().toString().trim().length() > 0 &&
                                skyltarAvstandExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getSkyltarAvstandExtraObjects();

                            showExtraObjects += String.valueOf(skyltarAvstandExtraName.getText()) + "      " +
                                    String.valueOf(skyltarAvstandExtraStatus.getText()) + "\n";

                            propertyList.setSkyltarAvstandExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            skyltarAvstandExtraName.setText("");
                            skyltarAvstandExtraStatus.setText("");

                            skyltarAvstandShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                skyltarAvstandTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("11_1");

                    }
                });

                pictogramExtraName = findViewById(R.id.pictogramExtraName);
                pictogramExtraStatus = findViewById(R.id.pictogramExtraStatus);
                pictogramSaveExtraObject = findViewById(R.id.pictogramSaveExtraObject);
                pictogramShowExtraObjects = findViewById(R.id.pictogramShowExtraObjects);
                pictogramTrash = findViewById(R.id.pictogramTrash);

                pictogramSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (pictogramExtraName.getText().toString().trim().length() > 0 &&
                                pictogramExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getPictogramExtraObjects();

                            showExtraObjects += String.valueOf(pictogramExtraName.getText()) + "      " +
                                    String.valueOf(pictogramExtraStatus.getText()) + "\n";

                            propertyList.setPictogramExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            pictogramExtraName.setText("");
                            pictogramExtraStatus.setText("");

                            pictogramShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                pictogramTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("11_2");

                    }
                });

                kontrastExtraName = findViewById(R.id.kontrastExtraName);
                kontrastExtraStatus = findViewById(R.id.kontrastExtraStatus);
                kontrastSaveExtraObject = findViewById(R.id.kontrastSaveExtraObject);
                kontrastShowExtraObjects = findViewById(R.id.kontrastShowExtraObjects);
                kontrastTrash = findViewById(R.id.kontrastTrash);

                kontrastSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (kontrastExtraName.getText().toString().trim().length() > 0 &&
                                kontrastExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getKontrastExtraObjects();

                            showExtraObjects += String.valueOf(kontrastExtraName.getText()) + "      " +
                                    String.valueOf(kontrastExtraStatus.getText()) + "\n";

                            propertyList.setKontrastExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            kontrastExtraName.setText("");
                            kontrastExtraStatus.setText("");

                            kontrastShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                kontrastTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("11_3");

                    }
                });

                enhetligExtraName = findViewById(R.id.enhetligExtraName);
                enhetligExtraStatus = findViewById(R.id.enhetligExtraStatus);
                enhetligSaveExtraObject = findViewById(R.id.enhetligSaveExtraObject);
                enhetligShowExtraObjects = findViewById(R.id.enhetligShowExtraObjects);
                enhetligTrash = findViewById(R.id.enhetligTrash);

                enhetligSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (enhetligExtraName.getText().toString().trim().length() > 0 &&
                                enhetligExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getEnhetligExtraObjects();

                            showExtraObjects += String.valueOf(enhetligExtraName.getText()) + "      " +
                                    String.valueOf(enhetligExtraStatus.getText()) + "\n";

                            propertyList.setEnhetligExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            enhetligExtraName.setText("");
                            enhetligExtraStatus.setText("");

                            enhetligShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                enhetligTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("11_4");

                    }
                });

                synligIAllaBelysningsforhallandenExtraName = findViewById(R.id.synligIAllaBelysningsforhallandenExtraName);
                synligIAllaBelysningsforhallandenExtraStatus = findViewById(R.id.synligIAllaBelysningsforhallandenExtraStatus);
                synligIAllaBelysningsforhallandenSaveExtraObject = findViewById(R.id.synligIAllaBelysningsforhallandenSaveExtraObject);
                synligIAllaBelysningsforhallandenShowExtraObjects = findViewById(R.id.synligIAllaBelysningsforhallandenShowExtraObjects);
                synligIAllaBelysningsforhallandenTrash = findViewById(R.id.synligIAllaBelysningsforhallandenTrash);

                synligIAllaBelysningsforhallandenSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (synligIAllaBelysningsforhallandenExtraName.getText().toString().trim().length() > 0 &&
                                synligIAllaBelysningsforhallandenExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getSynligIAllaBelysningsforhallandenExtraObjects();

                            showExtraObjects += String.valueOf(synligIAllaBelysningsforhallandenExtraName.getText()) + "      " +
                                    String.valueOf(synligIAllaBelysningsforhallandenExtraStatus.getText()) + "\n";

                            propertyList.setSynligIAllaBelysningsforhallandenExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            synligIAllaBelysningsforhallandenExtraName.setText("");
                            synligIAllaBelysningsforhallandenExtraStatus.setText("");

                            synligIAllaBelysningsforhallandenShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                synligIAllaBelysningsforhallandenTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("11_5");

                    }
                });

                skyltarEnligtISOExtraName = findViewById(R.id.skyltarEnligtISOExtraName);
                skyltarEnligtISOExtraStatus = findViewById(R.id.skyltarEnligtISOExtraStatus);
                skyltarEnligtISOSaveExtraObject = findViewById(R.id.skyltarEnligtISOSaveExtraObject);
                skyltarEnligtISOShowExtraObjects = findViewById(R.id.skyltarEnligtISOShowExtraObjects);
                skyltarEnligtISOTrash = findViewById(R.id.skyltarEnligtISOTrash);

                skyltarEnligtISOSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (skyltarEnligtISOExtraName.getText().toString().trim().length() > 0 &&
                                skyltarEnligtISOExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getSkyltarEnligtISOExtraObjects();

                            showExtraObjects += String.valueOf(skyltarEnligtISOExtraName.getText()) + "      " +
                                    String.valueOf(skyltarEnligtISOExtraStatus.getText()) + "\n";

                            propertyList.setSkyltarEnligtISOExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            skyltarEnligtISOExtraName.setText("");
                            skyltarEnligtISOExtraStatus.setText("");

                            skyltarEnligtISOShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                skyltarEnligtISOTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("11_6");

                    }
                });

                break;
            case 12:
                setContentView(R.layout.property_list_talad_information_sidoplattform);
                stipaNiva = findViewById(R.id.stipaNiva);

                stipaNivaExtraName = findViewById(R.id.stipaNivaExtraName);
                stipaNivaExtraStatus = findViewById(R.id.stipaNivaExtraStatus);
                stipaNivaSaveExtraObject = findViewById(R.id.stipaNivaSaveExtraObject);
                stipaNivaShowExtraObjects = findViewById(R.id.stipaNivaShowExtraObjects);
                stipaNivaTrash = findViewById(R.id.stipaNivaTrash);

                stipaNivaSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (stipaNivaExtraName.getText().toString().trim().length() > 0 &&
                                stipaNivaExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getStipaNivaExtraObjects();

                            showExtraObjects += String.valueOf(stipaNivaExtraName.getText()) + "      " +
                                    String.valueOf(stipaNivaExtraStatus.getText()) + "\n";

                            propertyList.setStipaNivaExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            stipaNivaExtraName.setText("");
                            stipaNivaExtraStatus.setText("");

                            stipaNivaShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                stipaNivaTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("12_1");

                    }
                });

                break;
            case 13:
                setContentView(R.layout.property_layout_plattformsbredd_och_plattformskant);
                forekomstAvRiskomrade = findViewById(R.id.forekomstAvRiskomrade);
                plattformsMinstaBredd = findViewById(R.id.plattformsMinstaBredd);
                avstandMellanLitetHinder = findViewById(R.id.avstandMellanLitetHinder);
                avstandMellanStortHinder = findViewById(R.id.avstandMellanStortHinder);
                markeringAvRiskomradetsGrans = findViewById(R.id.markeringAvRiskomradetsGrans);
                breddenPaVarningslinjeOchHalksakerhetOchFarg = findViewById(R.id.breddenPaVarningslinjeOchHalksakerhetOchFarg);
                materialPaPlattformskanten = findViewById(R.id.materialPaPlattformskanten);

                forekomstAvRiskomradeExtraName = findViewById(R.id.forekomstAvRiskomradeExtraName);
                forekomstAvRiskomradeExtraStatus = findViewById(R.id.forekomstAvRiskomradeExtraStatus);
                forekomstAvRiskomradeSaveExtraObject = findViewById(R.id.forekomstAvRiskomradeSaveExtraObject);
                forekomstAvRiskomradeShowExtraObjects = findViewById(R.id.forekomstAvRiskomradeShowExtraObjects);
                forekomstAvRiskomradeTrash = findViewById(R.id.forekomstAvRiskomradeTrash);

                forekomstAvRiskomradeSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (forekomstAvRiskomradeExtraName.getText().toString().trim().length() > 0 &&
                                forekomstAvRiskomradeExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getForekomstAvRiskomradeExtraObjects();

                            showExtraObjects += String.valueOf(forekomstAvRiskomradeExtraName.getText()) + "      " +
                                    String.valueOf(forekomstAvRiskomradeExtraStatus.getText()) + "\n";

                            propertyList.setForekomstAvRiskomradeExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            forekomstAvRiskomradeExtraName.setText("");
                            forekomstAvRiskomradeExtraStatus.setText("");

                            forekomstAvRiskomradeShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                forekomstAvRiskomradeTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("13_1");

                    }
                });

                plattformsMinstaBreddExtraName = findViewById(R.id.plattformsMinstaBreddExtraName);
                plattformsMinstaBreddExtraStatus = findViewById(R.id.plattformsMinstaBreddExtraStatus);
                plattformsMinstaBreddSaveExtraObject = findViewById(R.id.plattformsMinstaBreddSaveExtraObject);
                plattformsMinstaBreddShowExtraObjects = findViewById(R.id.plattformsMinstaBreddShowExtraObjects);
                plattformsMinstaBreddTrash = findViewById(R.id.plattformsMinstaBreddTrash);

                plattformsMinstaBreddSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (plattformsMinstaBreddExtraName.getText().toString().trim().length() > 0 &&
                                plattformsMinstaBreddExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getPlattformsMinstaBreddExtraObjects();

                            showExtraObjects += String.valueOf(plattformsMinstaBreddExtraName.getText()) + "      " +
                                    String.valueOf(plattformsMinstaBreddExtraStatus.getText()) + "\n";

                            propertyList.setPlattformsMinstaBreddExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            plattformsMinstaBreddExtraName.setText("");
                            plattformsMinstaBreddExtraStatus.setText("");

                            plattformsMinstaBreddShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                plattformsMinstaBreddTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("13_2");

                    }
                });

                avstandMellanLitetHinderExtraName = findViewById(R.id.avstandMellanLitetHinderExtraName);
                avstandMellanLitetHinderExtraStatus = findViewById(R.id.avstandMellanLitetHinderExtraStatus);
                avstandMellanLitetHinderSaveExtraObject = findViewById(R.id.avstandMellanLitetHinderSaveExtraObject);
                avstandMellanLitetHinderShowExtraObjects = findViewById(R.id.avstandMellanLitetHinderShowExtraObjects);
                avstandMellanLitetHinderTrash = findViewById(R.id.avstandMellanLitetHinderTrash);

                avstandMellanLitetHinderSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (avstandMellanLitetHinderExtraName.getText().toString().trim().length() > 0 &&
                                avstandMellanLitetHinderExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getAvstandMellanLitetHinderExtraObjects();

                            showExtraObjects += String.valueOf(avstandMellanLitetHinderExtraName.getText()) + "      " +
                                    String.valueOf(avstandMellanLitetHinderExtraStatus.getText()) + "\n";

                            propertyList.setAvstandMellanLitetHinderExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            avstandMellanLitetHinderExtraName.setText("");
                            avstandMellanLitetHinderExtraStatus.setText("");

                            avstandMellanLitetHinderShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                avstandMellanLitetHinderTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("13_3");

                    }
                });

                avstandMellanStortHinderExtraName = findViewById(R.id.avstandMellanStortHinderExtraName);
                avstandMellanStortHinderExtraStatus = findViewById(R.id.avstandMellanStortHinderExtraStatus);
                avstandMellanStortHinderSaveExtraObject = findViewById(R.id.avstandMellanStortHinderSaveExtraObject);
                avstandMellanStortHinderShowExtraObjects = findViewById(R.id.avstandMellanStortHinderShowExtraObjects);
                avstandMellanStortHinderTrash = findViewById(R.id.avstandMellanStortHinderTrash);

                avstandMellanStortHinderSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (avstandMellanStortHinderExtraName.getText().toString().trim().length() > 0 &&
                                avstandMellanStortHinderExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getAvstandMellanStortHinderExtraObjects();

                            showExtraObjects += String.valueOf(avstandMellanStortHinderExtraName.getText()) + "      " +
                                    String.valueOf(avstandMellanStortHinderExtraStatus.getText()) + "\n";

                            propertyList.setAvstandMellanStortHinderExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            avstandMellanStortHinderExtraName.setText("");
                            avstandMellanStortHinderExtraStatus.setText("");

                            avstandMellanStortHinderShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                avstandMellanStortHinderTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("13_4");

                    }
                });

                markeringAvRiskomradetsGransExtraName = findViewById(R.id.markeringAvRiskomradetsGransExtraName);
                markeringAvRiskomradetsGransExtraStatus = findViewById(R.id.markeringAvRiskomradetsGransExtraStatus);
                markeringAvRiskomradetsGransSaveExtraObject = findViewById(R.id.markeringAvRiskomradetsGransSaveExtraObject);
                markeringAvRiskomradetsGransShowExtraObjects = findViewById(R.id.markeringAvRiskomradetsGransShowExtraObjects);
                markeringAvRiskomradetsGransTrash = findViewById(R.id.markeringAvRiskomradetsGransTrash);

                markeringAvRiskomradetsGransSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (markeringAvRiskomradetsGransExtraName.getText().toString().trim().length() > 0 &&
                                markeringAvRiskomradetsGransExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getMarkeringAvRiskomradetsGransExtraObjects();

                            showExtraObjects += String.valueOf(markeringAvRiskomradetsGransExtraName.getText()) + "      " +
                                    String.valueOf(markeringAvRiskomradetsGransExtraStatus.getText()) + "\n";

                            propertyList.setMarkeringAvRiskomradetsGransExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            markeringAvRiskomradetsGransExtraName.setText("");
                            markeringAvRiskomradetsGransExtraStatus.setText("");

                            markeringAvRiskomradetsGransShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                markeringAvRiskomradetsGransTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("13_5");

                    }
                });

                breddenPaVarningslinjeOchHalksakerhetOchFargExtraName = findViewById(R.id.breddenPaVarningslinjeOchHalksakerhetOchFargExtraName);
                breddenPaVarningslinjeOchHalksakerhetOchFargExtraStatus = findViewById(R.id.breddenPaVarningslinjeOchHalksakerhetOchFargExtraStatus);
                breddenPaVarningslinjeOchHalksakerhetOchFargSaveExtraObject = findViewById(R.id.breddenPaVarningslinjeOchHalksakerhetOchFargSaveExtraObject);
                breddenPaVarningslinjeOchHalksakerhetOchFargShowExtraObjects = findViewById(R.id.breddenPaVarningslinjeOchHalksakerhetOchFargShowExtraObjects);
                breddenPaVarningslinjeOchHalksakerhetOchFargTrash = findViewById(R.id.breddenPaVarningslinjeOchHalksakerhetOchFargTrash);

                breddenPaVarningslinjeOchHalksakerhetOchFargSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (breddenPaVarningslinjeOchHalksakerhetOchFargExtraName.getText().toString().trim().length() > 0 &&
                                breddenPaVarningslinjeOchHalksakerhetOchFargExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getBreddenPaVarningslinjeOchHalksakerhetOchFargExtraObjects();

                            showExtraObjects += String.valueOf(breddenPaVarningslinjeOchHalksakerhetOchFargExtraName.getText()) + "      " +
                                    String.valueOf(breddenPaVarningslinjeOchHalksakerhetOchFargExtraStatus.getText()) + "\n";

                            propertyList.setBreddenPaVarningslinjeOchHalksakerhetOchFargExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            breddenPaVarningslinjeOchHalksakerhetOchFargExtraName.setText("");
                            breddenPaVarningslinjeOchHalksakerhetOchFargExtraStatus.setText("");

                            breddenPaVarningslinjeOchHalksakerhetOchFargShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                breddenPaVarningslinjeOchHalksakerhetOchFargTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("13_6");

                    }
                });

                materialPaPlattformskantenExtraName = findViewById(R.id.materialPaPlattformskantenExtraName);
                materialPaPlattformskantenExtraStatus = findViewById(R.id.materialPaPlattformskantenExtraStatus);
                materialPaPlattformskantenSaveExtraObject = findViewById(R.id.materialPaPlattformskantenSaveExtraObject);
                materialPaPlattformskantenShowExtraObjects = findViewById(R.id.materialPaPlattformskantenShowExtraObjects);
                materialPaPlattformskantenTrash = findViewById(R.id.materialPaPlattformskantenTrash);

                materialPaPlattformskantenSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (materialPaPlattformskantenExtraName.getText().toString().trim().length() > 0 &&
                                materialPaPlattformskantenExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getMaterialPaPlattformskantenExtraObjects();

                            showExtraObjects += String.valueOf(materialPaPlattformskantenExtraName.getText()) + "      " +
                                    String.valueOf(materialPaPlattformskantenExtraStatus.getText()) + "\n";

                            propertyList.setMaterialPaPlattformskantenExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            materialPaPlattformskantenExtraName.setText("");
                            materialPaPlattformskantenExtraStatus.setText("");

                            materialPaPlattformskantenShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                materialPaPlattformskantenTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("13_7");

                    }
                });

                break;
            case 14:
                setContentView(R.layout.property_list_plattformens_slut);
                markeringAvPlattformensSlut = findViewById(R.id.markeringAvPlattformensSlut);

                markeringAvPlattformensSlutExtraName = findViewById(R.id.markeringAvPlattformensSlutExtraName);
                markeringAvPlattformensSlutExtraStatus = findViewById(R.id.markeringAvPlattformensSlutExtraStatus);
                markeringAvPlattformensSlutSaveExtraObject = findViewById(R.id.markeringAvPlattformensSlutSaveExtraObject);
                markeringAvPlattformensSlutShowExtraObjects = findViewById(R.id.markeringAvPlattformensSlutShowExtraObjects);
                markeringAvPlattformensSlutTrash = findViewById(R.id.markeringAvPlattformensSlutTrash);

                markeringAvPlattformensSlutSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (markeringAvPlattformensSlutExtraName.getText().toString().trim().length() > 0 &&
                                markeringAvPlattformensSlutExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getMarkeringAvPlattformensSlutExtraObjects();

                            showExtraObjects += String.valueOf(markeringAvPlattformensSlutExtraName.getText()) + "      " +
                                    String.valueOf(markeringAvPlattformensSlutExtraStatus.getText()) + "\n";

                            propertyList.setMarkeringAvPlattformensSlutExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            markeringAvPlattformensSlutExtraName.setText("");
                            markeringAvPlattformensSlutExtraStatus.setText("");

                            markeringAvPlattformensSlutShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                markeringAvPlattformensSlutTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("14_1");

                    }
                });

                break;
            case 15:
                setContentView(R.layout.property_list_sparkorsning_for_passagerare_pa_vag_till_plattformar);
                anvandsSomEnDelAvTrappstegfriGangvag = findViewById(R.id.anvandsSomEnDelAvTrappstegfriGangvag);
                breddPaGangvagg = findViewById(R.id.breddPaGangvagg);
                lutning = findViewById(R.id.lutning);
                friPassageForMinstaHjuletPaEnRullstol = findViewById(R.id.friPassageForMinstaHjuletPaEnRullstol);
                friPassageOmSakerhetschikanerForekommer = findViewById(R.id.friPassageOmSakerhetschikanerForekommer);
                markeringAvGangbaneytan = findViewById(R.id.markeringAvGangbaneytan);
                sakerPassage = findViewById(R.id.sakerPassage);

                anvandsSomEnDelAvTrappstegfriGangvagExtraName = findViewById(R.id.anvandsSomEnDelAvTrappstegfriGangvagExtraName);
                anvandsSomEnDelAvTrappstegfriGangvagExtraStatus = findViewById(R.id.anvandsSomEnDelAvTrappstegfriGangvagExtraStatus);
                anvandsSomEnDelAvTrappstegfriGangvagSaveExtraObjects = findViewById(R.id.anvandsSomEnDelAvTrappstegfriGangvagSaveExtraObject);
                anvandsSomEnDelAvTrappstegfriGangvagShowExtraObjects = findViewById(R.id.anvandsSomEnDelAvTrappstegfriGangvagShowExtraObjects);
                anvandsSomEnDelAvTrappstegfriGangvagTrash = findViewById(R.id.anvandsSomEnDelAvTrappstegfriGangvagTrash);

                anvandsSomEnDelAvTrappstegfriGangvagSaveExtraObjects.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (anvandsSomEnDelAvTrappstegfriGangvagExtraName.getText().toString().trim().length() > 0 &&
                                anvandsSomEnDelAvTrappstegfriGangvagExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getAnvandsSomEnDelAvTrappstegfriGangvagExtraObjects();

                            showExtraObjects += String.valueOf(anvandsSomEnDelAvTrappstegfriGangvagExtraName.getText()) + "      " +
                                    String.valueOf(anvandsSomEnDelAvTrappstegfriGangvagExtraStatus.getText()) + "\n";

                            propertyList.setAnvandsSomEnDelAvTrappstegfriGangvagExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            anvandsSomEnDelAvTrappstegfriGangvagExtraName.setText("");
                            anvandsSomEnDelAvTrappstegfriGangvagExtraStatus.setText("");

                            anvandsSomEnDelAvTrappstegfriGangvagShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                anvandsSomEnDelAvTrappstegfriGangvagTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("15_1");

                    }
                });

                breddPaGangvaggExtraName = findViewById(R.id.breddPaGangvaggExtraName);
                breddPaGangvaggExtraStatus = findViewById(R.id.breddPaGangvaggExtraStatus);
                breddPaGangvaggSaveExtraObject = findViewById(R.id.breddPaGangvaggSaveExtraObject);
                breddPaGangvaggShowExtraObjects = findViewById(R.id.breddPaGangvaggShowExtraObjects);
                breddPaGangvaggTrash = findViewById(R.id.breddPaGangvaggTrash);

                breddPaGangvaggSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (breddPaGangvaggExtraName.getText().toString().trim().length() > 0 &&
                                breddPaGangvaggExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getBreddPaGangvaggExtraObjects();

                            showExtraObjects += String.valueOf(breddPaGangvaggExtraName.getText()) + "      " +
                                    String.valueOf(breddPaGangvaggExtraStatus.getText()) + "\n";

                            propertyList.setBreddPaGangvaggExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            breddPaGangvaggExtraName.setText("");
                            breddPaGangvaggExtraStatus.setText("");

                            breddPaGangvaggShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                breddPaGangvaggTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("15_2");

                    }
                });

                lutningExtraName = findViewById(R.id.lutningExtraName);
                lutningExtraStatus = findViewById(R.id.lutningExtraStatus);
                lutningSaveExtraObject = findViewById(R.id.lutningSaveExtraObject);
                lutningShowExtraObjects = findViewById(R.id.lutningShowExtraObjects);
                lutningTrash = findViewById(R.id.lutningTrash);

                lutningSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (lutningExtraName.getText().toString().trim().length() > 0 &&
                                lutningExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getLutningExtraObjects();

                            showExtraObjects += String.valueOf(lutningExtraName.getText()) + "      " +
                                    String.valueOf(lutningExtraStatus.getText()) + "\n";

                            propertyList.setLutningExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            lutningExtraName.setText("");
                            lutningExtraStatus.setText("");

                            lutningShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                lutningTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("15_3");

                    }
                });

                friPassageForMinstaHjuletPaEnRullstolExtraName = findViewById(R.id.friPassageForMinstaHjuletPaEnRullstolExtraName);
                friPassageForMinstaHjuletPaEnRullstolExtraStatus = findViewById(R.id.friPassageForMinstaHjuletPaEnRullstolExtraStatus);
                friPassageForMinstaHjuletPaEnRullstolSaveExtraObject = findViewById(R.id.friPassageForMinstaHjuletPaEnRullstolSaveExtraObject);
                friPassageForMinstaHjuletPaEnRullstolShowExtraObjects = findViewById(R.id.friPassageForMinstaHjuletPaEnRullstolShowExtraObjects);
                friPassageForMinstaHjuletPaEnRullstolTrash = findViewById(R.id.friPassageForMinstaHjuletPaEnRullstolTrash);

                friPassageForMinstaHjuletPaEnRullstolSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (friPassageForMinstaHjuletPaEnRullstolExtraName.getText().toString().trim().length() > 0 &&
                                friPassageForMinstaHjuletPaEnRullstolExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getFriPassageForMinstaHjuletPaEnRullstolExtraObjects();

                            showExtraObjects += String.valueOf(friPassageForMinstaHjuletPaEnRullstolExtraName.getText()) + "      " +
                                    String.valueOf(friPassageForMinstaHjuletPaEnRullstolExtraStatus.getText()) + "\n";

                            propertyList.setFriPassageForMinstaHjuletPaEnRullstolExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            friPassageForMinstaHjuletPaEnRullstolExtraName.setText("");
                            friPassageForMinstaHjuletPaEnRullstolExtraStatus.setText("");

                            friPassageForMinstaHjuletPaEnRullstolShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                friPassageForMinstaHjuletPaEnRullstolTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("15_4");

                    }
                });

                friPassageOmSakerhetschikanerForekommerExtraName = findViewById(R.id.friPassageOmSakerhetschikanerForekommerExtraName);
                friPassageOmSakerhetschikanerForekommerExtraStatus = findViewById(R.id.friPassageOmSakerhetschikanerForekommerExtraStatus);
                friPassageOmSakerhetschikanerForekommerSaveExtraObject = findViewById(R.id.friPassageOmSakerhetschikanerForekommerSaveExtraObject);
                friPassageOmSakerhetschikanerForekommerShowExtraObjects = findViewById(R.id.friPassageOmSakerhetschikanerForekommerShowExtraObjects);
                friPassageOmSakerhetschikanerForekommerTrash = findViewById(R.id.friPassageOmSakerhetschikanerForekommerTrash);

                friPassageOmSakerhetschikanerForekommerSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (friPassageOmSakerhetschikanerForekommerExtraName.getText().toString().trim().length() > 0 &&
                                friPassageOmSakerhetschikanerForekommerExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getFriPassageOmSakerhetschikanerForekommerExtraObjects();

                            showExtraObjects += String.valueOf(friPassageOmSakerhetschikanerForekommerExtraName.getText()) + "      " +
                                    String.valueOf(friPassageOmSakerhetschikanerForekommerExtraStatus.getText()) + "\n";

                            propertyList.setFriPassageOmSakerhetschikanerForekommerExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            friPassageOmSakerhetschikanerForekommerExtraName.setText("");
                            friPassageOmSakerhetschikanerForekommerExtraStatus.setText("");

                            friPassageOmSakerhetschikanerForekommerShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                friPassageOmSakerhetschikanerForekommerTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("15_5");

                    }
                });

                markeringAvGangbaneytanExtraName = findViewById(R.id.markeringAvGangbaneytanExtraName);
                markeringAvGangbaneytanExtraStatus = findViewById(R.id.markeringAvGangbaneytanExtraStatus);
                markeringAvGangbaneytanSaveExtraObject = findViewById(R.id.markeringAvGangbaneytanSaveExtraObject);
                markeringAvGangbaneytanShowExtraObjects = findViewById(R.id.markeringAvGangbaneytanShowExtraObjects);
                markeringAvGangbaneytanTrash = findViewById(R.id.markeringAvGangbaneytanTrash);

                markeringAvGangbaneytanSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (markeringAvGangbaneytanExtraName.getText().toString().trim().length() > 0 &&
                                markeringAvGangbaneytanExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getMarkeringAvGangbaneytanExtraObjects();

                            showExtraObjects += String.valueOf(markeringAvGangbaneytanExtraName.getText()) + "      " +
                                    String.valueOf(markeringAvGangbaneytanExtraStatus.getText()) + "\n";

                            propertyList.setMarkeringAvGangbaneytanExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            markeringAvGangbaneytanExtraName.setText("");
                            markeringAvGangbaneytanExtraStatus.setText("");

                            markeringAvGangbaneytanShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                markeringAvGangbaneytanTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("15_6");

                    }
                });

                sakerPassageExtraName = findViewById(R.id.sakerPassageExtraName);
                sakerPassageExtraStatus = findViewById(R.id.sakerPassageExtraStatus);
                sakerPassageSaveExtraObject = findViewById(R.id.sakerPassageSaveExtraObject);
                sakerPassageShowExtraObjects = findViewById(R.id.sakerPassageShowExtraObjects);
                sakerPassageTrash = findViewById(R.id.sakerPassageTrash);

                sakerPassageSaveExtraObject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (sakerPassageExtraName.getText().toString().trim().length() > 0 &&
                                sakerPassageExtraStatus.getText().toString().trim().length() > 0) {

                            String showExtraObjects = propertyList.getSakerPassageExtraObjects();

                            showExtraObjects += String.valueOf(sakerPassageExtraName.getText()) + "      " +
                                    String.valueOf(sakerPassageExtraStatus.getText()) + "\n";

                            propertyList.setSakerPassageExtraObjects(showExtraObjects);
                            databases.updatePropertyListData(propertyList);

                            sakerPassageExtraName.setText("");
                            sakerPassageExtraStatus.setText("");

                            sakerPassageShowExtraObjects.setText(showExtraObjects);

                        } else {
                            toast = Toast.makeText(getApplicationContext(),
                                    "Båda fält måste vara ifyllda",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                    }
                });

                sakerPassageTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog("15_7");
                    }
                });

                break;
        }
        insertValues();
    }

    public void showDialog(final String trash) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Rensa hela listan");
        String message = "Är du säker på att du vill rensa hela listan?";
        builder.setMessage(message);

        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                switch (trash) {
                    case "1_1":
                        parkeringsmojligheterForFunktionshindradeShowExtraObjects.setText("");
                        propertyList.setParkeringsmojligheterForFunktionshindradeExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "1_2":
                        placeringAvParkeringForFunktionshindradeShowExtraObjects.setText("");
                        propertyList.setPlaceringAvParkeringForFunktionshindradeExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "2_1":
                        forekomstAvHinderfriGangvagShowExtraObjects.setText("");
                        propertyList.setForekomstAvHinderfriGangvagExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "2_2":
                        langdenPaHindergriaGangvagarShowExtraObjects.setText("");
                        propertyList.setLangdenPaHindergriaGangvagarExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "2_3":
                        reflekterandeEgenskaperShowExtraObjects.setText("");
                        propertyList.setReflekterandeEgenskaperExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "3_1":
                        hinderfriGangvagsbreddShowExtraObjects.setText("");
                        propertyList.setHinderfriGangvagsbreddExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "3_2":
                        trosklarPaHinderfriGangvagShowExtraObjects.setText("");
                        propertyList.setTrosklarPaHinderfriGangvagExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "4_1":
                        trappstegsfriVagShowExtraObjects.setText("");
                        propertyList.setTrappstegsfriVagExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "4_2":
                        breddPaTrapporShowExtraObjects.setText("");
                        propertyList.setBreddPaTrapporExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "4_3":
                        visuelMarkeringPaForstaOchSistaStegetShowExtraObjects.setText("");
                        propertyList.setVisuelMarkeringPaForstaOchSistaStegetExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "4_4":
                        taktilVarningForeForstaUppgaendeTrappstegShowExtraObjects.setText("");
                        propertyList.setTaktilVarningForeForstaUppgaendeTrappstegExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "4_5":
                        ramperForPersonerMedFunktionsnedsättningarShowExtraObjects.setText("");
                        propertyList.setRamperForPersonerMedFunktionsnedsattningarExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "4_6":
                        ledstangerPaBadaSidorOchTvaNivaerShowExtraObjects.setText("");
                        propertyList.setLedstangerPaBadaSidorOchTvaNivaerExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "4_7":
                        hissarShowExtraObjects.setText("");
                        propertyList.setHissarExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "4_8":
                        rulltrapporOchRullramperShowExtraObjects.setText("");
                        propertyList.setRulltrapporOchRullramperExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "4_9":
                        plankorsningarShowExtraObjects.setText("");
                        propertyList.setPlankorsningarExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "5_1":
                        tydligMarkeringShowExtraObjects.setText("");
                        propertyList.setTydligMarkeringExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "5_2":
                        tillhandahållandeAvInformationTillSynskadadeShowExtraObjects.setText("");
                        propertyList.setTillhandahållandeAvInformationTillSynskadadeExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "5_3":
                        fjarrstyrdaLjudanordningarEllerTeleapplikationerShowExtraObjects.setText("");
                        propertyList.setFjarrstyrdaLjudanordningarEllerTeleapplikationerExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "5_4":
                        taktilInformationPaLedstangerEllerVaggarShowExtraObjects.setText("");
                        propertyList.setTaktilInformationPaLedstangerEllerVaggarExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "6_1":
                        halksakerhetShowExtraObjects.setText("");
                        propertyList.setHalksakerhetExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "6_2":
                        ojamnheterSomOverstigerShowExtraObjects.setText("");
                        propertyList.setOjamnheterSomOverstigerExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "7_1":
                        glasdorrarEllerGenomskinligaVaggarLangsGangvagarShowExtraObjects.setText("");
                        propertyList.setGlasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "8_1":
                        toalettutrymmeAnpassatForRullstolsburnaPersonerShowExtraObjects.setText("");
                        propertyList.setToalettutrymmeAnpassatForRullstolsburnaPersonerExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "8_2":
                        skotplatserTillgangligaForBadeKonenShowExtraObjects.setText("");
                        propertyList.setSkotplatserTillgangligaForBadeKonenExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "9_1":
                        kontrastMotBakgrundOchAvrundandeKanterShowExtraObjects.setText("");
                        propertyList.setKontrastMotBakgrundOchAvrundandeKanterExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "9_2":
                        placeringAvInredningOchEnheterShowExtraObjects.setText("");
                        propertyList.setPlaceringAvInredningOchEnheterExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "9_3":
                        sittmojligheterOchPlatsForEnRullstolsbundenPersonShowExtraObjects.setText("");
                        propertyList.setSittmojligheterOchPlatsForEnRullstolsbundenPersonExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "9_4":
                        vaderskyddatOmradeShowExtraObjects.setText("");
                        propertyList.setVaderskyddatOmradeExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "10_1":
                        belysningPaStationensExternaOmradenShowExtraObjects.setText("");
                        propertyList.setBelysningPaStationensExternaOmradenExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "10_2":
                        belysningLangsHinderfriaGangvagarShowExtraObjects.setText("");
                        propertyList.setBelysningLangsHinderfriaGangvagarExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "10_3":
                        belysningPaPlattformarShowExtraObjects.setText("");
                        propertyList.setBelysningPaPlattformarExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "10_4":
                        nodbelysningShowExtraObjects.setText("");
                        propertyList.setNodbelysningExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "11_1":
                        skyltarAvstandShowExtraObjects.setText("");
                        propertyList.setSkyltarAvstandExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "11_2":
                        pictogramShowExtraObjects.setText("");
                        propertyList.setPictogramExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "11_3":
                        kontrastShowExtraObjects.setText("");
                        propertyList.setKontrastExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "11_4":
                        enhetligShowExtraObjects.setText("");
                        propertyList.setEnhetligExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "11_5":
                        synligIAllaBelysningsforhallandenShowExtraObjects.setText("");
                        propertyList.setSynligIAllaBelysningsforhallandenExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "11_6":
                        skyltarEnligtISOShowExtraObjects.setText("");
                        propertyList.setSkyltarEnligtISOExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "12_1":
                        stipaNivaShowExtraObjects.setText("");
                        propertyList.setStipaNivaExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "13_1":
                        forekomstAvRiskomradeShowExtraObjects.setText("");
                        propertyList.setForekomstAvRiskomradeExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "13_2":
                        plattformsMinstaBreddShowExtraObjects.setText("");
                        propertyList.setPlattformsMinstaBreddExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "13_3":
                        avstandMellanLitetHinderShowExtraObjects.setText("");
                        propertyList.setAvstandMellanLitetHinderExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "13_4":
                        avstandMellanStortHinderShowExtraObjects.setText("");
                        propertyList.setAvstandMellanStortHinderExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "13_5":
                        markeringAvRiskomradetsGransShowExtraObjects.setText("");
                        propertyList.setMarkeringAvRiskomradetsGransExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "13_6":
                        breddenPaVarningslinjeOchHalksakerhetOchFargShowExtraObjects.setText("");
                        propertyList.setBreddenPaVarningslinjeOchHalksakerhetOchFargExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "13_7":
                        materialPaPlattformskantenShowExtraObjects.setText("");
                        propertyList.setMaterialPaPlattformskantenExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "14_1":
                        markeringAvPlattformensSlutShowExtraObjects.setText("");
                        propertyList.setMarkeringAvPlattformensSlutExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "15_1":
                        anvandsSomEnDelAvTrappstegfriGangvagShowExtraObjects.setText("");
                        propertyList.setAnvandsSomEnDelAvTrappstegfriGangvagExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "15_2":
                        breddPaGangvaggShowExtraObjects.setText("");
                        propertyList.setBreddPaGangvaggExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "15_3":
                        lutningShowExtraObjects.setText("");
                        propertyList.setLutningExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "15_4":
                        friPassageForMinstaHjuletPaEnRullstolShowExtraObjects.setText("");
                        propertyList.setFriPassageForMinstaHjuletPaEnRullstolExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "15_5":
                        friPassageOmSakerhetschikanerForekommerShowExtraObjects.setText("");
                        propertyList.setFriPassageOmSakerhetschikanerForekommerExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "15_6":
                        markeringAvGangbaneytanShowExtraObjects.setText("");
                        propertyList.setMarkeringAvGangbaneytanExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                    case "15_7":
                        sakerPassageShowExtraObjects.setText("");
                        propertyList.setSakerPassageExtraObjects("");
                        databases.updatePropertyListData(propertyList);
                        break;
                }

                toast = Toast.makeText(getApplicationContext(),
                        "Listan är rensad",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        builder.setNegativeButton("Avbryt", null);

        builder.setNeutralButton("Radera senaste objektet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String testing;
                ArrayList aList;
                String newString;
                int indexToRemove;

                switch (trash) {
                    case "1_1":

                        testing = propertyList.getParkeringsmojligheterForFunktionshindradeExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            parkeringsmojligheterForFunktionshindradeShowExtraObjects.setText(newString);
                            propertyList.setParkeringsmojligheterForFunktionshindradeExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "1_2":

                        testing = propertyList.getPlaceringAvParkeringForFunktionshindradeExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            placeringAvParkeringForFunktionshindradeShowExtraObjects.setText(newString);
                            propertyList.setPlaceringAvParkeringForFunktionshindradeExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "2_1":
                        testing = propertyList.getPlaceringAvParkeringForFunktionshindradeExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }

                            forekomstAvHinderfriGangvagShowExtraObjects.setText("");
                            propertyList.setForekomstAvHinderfriGangvagExtraObjects("");
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "2_2":
                        testing = propertyList.getLangdenPaHindergriaGangvagarExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            langdenPaHindergriaGangvagarShowExtraObjects.setText(newString);
                            propertyList.setLangdenPaHindergriaGangvagarExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "2_3":
                        testing = propertyList.getReflekterandeEgenskaperExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            reflekterandeEgenskaperShowExtraObjects.setText(newString);
                            propertyList.setReflekterandeEgenskaperExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "3_1":
                        testing = propertyList.getHinderfriGangvagsbreddExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            hinderfriGangvagsbreddShowExtraObjects.setText(newString);
                            propertyList.setHinderfriGangvagsbreddExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "3_2":
                        testing = propertyList.getTrosklarPaHinderfriGangvagExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            trosklarPaHinderfriGangvagShowExtraObjects.setText(newString);
                            propertyList.setTrosklarPaHinderfriGangvagExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "4_1":
                        testing = propertyList.getTrappstegsfriVagExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            trappstegsfriVagShowExtraObjects.setText(newString);
                            propertyList.setTrappstegsfriVagExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "4_2":
                        testing = propertyList.getBreddPaTrapporExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            breddPaTrapporShowExtraObjects.setText(newString);
                            propertyList.setBreddPaTrapporExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "4_3":
                        testing = propertyList.getVisuelMarkeringPaForstaOchSistaStegetExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            visuelMarkeringPaForstaOchSistaStegetShowExtraObjects.setText(newString);
                            propertyList.setVisuelMarkeringPaForstaOchSistaStegetExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "4_4":
                        testing = propertyList.getTaktilVarningForeForstaUppgaendeTrappstegExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            taktilVarningForeForstaUppgaendeTrappstegShowExtraObjects.setText(newString);
                            propertyList.setTaktilVarningForeForstaUppgaendeTrappstegExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "4_5":
                        testing = propertyList.getRamperForPersonerMedFunktionsnedsattningarExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            ramperForPersonerMedFunktionsnedsättningarShowExtraObjects.setText(newString);
                            propertyList.setRamperForPersonerMedFunktionsnedsattningarExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "4_6":
                        testing = propertyList.getLedstangerPaBadaSidorOchTvaNivaerExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            ledstangerPaBadaSidorOchTvaNivaerShowExtraObjects.setText(newString);
                            propertyList.setLedstangerPaBadaSidorOchTvaNivaerExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "4_7":
                        testing = propertyList.getHissarExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            hissarShowExtraObjects.setText(newString);
                            propertyList.setHissarExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "4_8":
                        testing = propertyList.getRulltrapporOchRullramperExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            rulltrapporOchRullramperShowExtraObjects.setText(newString);
                            propertyList.setRulltrapporOchRullramperExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "4_9":
                        testing = propertyList.getPlankorsningarExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            plankorsningarShowExtraObjects.setText(newString);
                            propertyList.setPlankorsningarExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "5_1":
                        testing = propertyList.getTydligMarkeringExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            tydligMarkeringShowExtraObjects.setText(newString);
                            propertyList.setTydligMarkeringExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "5_2":
                        testing = propertyList.getTillhandahållandeAvInformationTillSynskadadeExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            tillhandahållandeAvInformationTillSynskadadeShowExtraObjects.setText(newString);
                            propertyList.setTillhandahållandeAvInformationTillSynskadadeExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "5_3":
                        testing = propertyList.getFjarrstyrdaLjudanordningarEllerTeleapplikationerExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            fjarrstyrdaLjudanordningarEllerTeleapplikationerShowExtraObjects.setText(newString);
                            propertyList.setFjarrstyrdaLjudanordningarEllerTeleapplikationerExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "5_4":
                        testing = propertyList.getTaktilInformationPaLedstangerEllerVaggarExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            taktilInformationPaLedstangerEllerVaggarShowExtraObjects.setText(newString);
                            propertyList.setTaktilInformationPaLedstangerEllerVaggarExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "6_1":
                        testing = propertyList.getHalksakerhetExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            halksakerhetShowExtraObjects.setText(newString);
                            propertyList.setHalksakerhetExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "6_2":
                        testing = propertyList.getOjamnheterSomOverstigerExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            ojamnheterSomOverstigerShowExtraObjects.setText(newString);
                            propertyList.setOjamnheterSomOverstigerExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "7_1":
                        testing = propertyList.getGlasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            glasdorrarEllerGenomskinligaVaggarLangsGangvagarShowExtraObjects.setText(newString);
                            propertyList.setGlasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "8_1":
                        testing = propertyList.getToalettutrymmeAnpassatForRullstolsburnaPersonerExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            toalettutrymmeAnpassatForRullstolsburnaPersonerShowExtraObjects.setText(newString);
                            propertyList.setToalettutrymmeAnpassatForRullstolsburnaPersonerExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "8_2":
                        testing = propertyList.getSkotplatserTillgangligaForBadeKonenExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            skotplatserTillgangligaForBadeKonenShowExtraObjects.setText(newString);
                            propertyList.setSkotplatserTillgangligaForBadeKonenExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "9_1":
                        testing = propertyList.getKontrastMotBakgrundOchAvrundandeKanterExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            kontrastMotBakgrundOchAvrundandeKanterShowExtraObjects.setText(newString);
                            propertyList.setKontrastMotBakgrundOchAvrundandeKanterExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "9_2":
                        testing = propertyList.getPlaceringAvInredningOchEnheterExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            placeringAvInredningOchEnheterShowExtraObjects.setText(newString);
                            propertyList.setPlaceringAvInredningOchEnheterExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "9_3":
                        testing = propertyList.getSittmojligheterOchPlatsForEnRullstolsbundenPersonExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            sittmojligheterOchPlatsForEnRullstolsbundenPersonShowExtraObjects.setText(newString);
                            propertyList.setSittmojligheterOchPlatsForEnRullstolsbundenPersonExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "9_4":
                        testing = propertyList.getVaderskyddatOmradeExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            vaderskyddatOmradeShowExtraObjects.setText(newString);
                            propertyList.setVaderskyddatOmradeExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "10_1":
                        testing = propertyList.getBelysningPaStationensExternaOmradenExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            belysningPaStationensExternaOmradenShowExtraObjects.setText(newString);
                            propertyList.setBelysningPaStationensExternaOmradenExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "10_2":
                        testing = propertyList.getPlaceringAvParkeringForFunktionshindradeExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            belysningLangsHinderfriaGangvagarShowExtraObjects.setText(newString);
                            propertyList.setBelysningLangsHinderfriaGangvagarExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "10_3":
                        testing = propertyList.getBelysningPaPlattformarExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            belysningPaPlattformarShowExtraObjects.setText(newString);
                            propertyList.setBelysningPaPlattformarExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "10_4":
                        testing = propertyList.getNodbelysningExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            nodbelysningShowExtraObjects.setText(newString);
                            propertyList.setNodbelysningExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "11_1":
                        testing = propertyList.getSkyltarAvstandExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            skyltarAvstandShowExtraObjects.setText(newString);
                            propertyList.setSkyltarAvstandExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "11_2":
                        testing = propertyList.getPictogramExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            pictogramShowExtraObjects.setText(newString);
                            propertyList.setPictogramExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "11_3":
                        testing = propertyList.getKontrastExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            kontrastShowExtraObjects.setText(newString);
                            propertyList.setKontrastExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "11_4":
                        testing = propertyList.getEnhetligExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            enhetligShowExtraObjects.setText(newString);
                            propertyList.setEnhetligExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "11_5":
                        testing = propertyList.getSynligIAllaBelysningsforhallandenExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            synligIAllaBelysningsforhallandenShowExtraObjects.setText(newString);
                            propertyList.setSynligIAllaBelysningsforhallandenExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "11_6":
                        testing = propertyList.getSkyltarEnligtISOExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            skyltarEnligtISOShowExtraObjects.setText(newString);
                            propertyList.setSkyltarEnligtISOExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "12_1":
                        testing = propertyList.getStipaNivaExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            stipaNivaShowExtraObjects.setText(newString);
                            propertyList.setStipaNivaExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "13_1":
                        testing = propertyList.getForekomstAvRiskomradeExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            forekomstAvRiskomradeShowExtraObjects.setText(newString);
                            propertyList.setForekomstAvRiskomradeExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "13_2":
                        testing = propertyList.getPlattformsMinstaBreddExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            plattformsMinstaBreddShowExtraObjects.setText(newString);
                            propertyList.setPlattformsMinstaBreddExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "13_3":
                        testing = propertyList.getAvstandMellanLitetHinderExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            avstandMellanLitetHinderShowExtraObjects.setText(newString);
                            propertyList.setAvstandMellanLitetHinderExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "13_4":
                        testing = propertyList.getAvstandMellanStortHinderExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            avstandMellanStortHinderShowExtraObjects.setText(newString);
                            propertyList.setAvstandMellanStortHinderExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "13_5":
                        testing = propertyList.getMarkeringAvRiskomradetsGransExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            markeringAvRiskomradetsGransShowExtraObjects.setText(newString);
                            propertyList.setMarkeringAvRiskomradetsGransExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "13_6":
                        testing = propertyList.getBreddenPaVarningslinjeOchHalksakerhetOchFargExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            breddenPaVarningslinjeOchHalksakerhetOchFargShowExtraObjects.setText(newString);
                            propertyList.setBreddenPaVarningslinjeOchHalksakerhetOchFargExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "13_7":
                        testing = propertyList.getMaterialPaPlattformskantenExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            materialPaPlattformskantenShowExtraObjects.setText(newString);
                            propertyList.setMaterialPaPlattformskantenExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "14_1":
                        testing = propertyList.getMarkeringAvPlattformensSlutExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            markeringAvPlattformensSlutShowExtraObjects.setText(newString);
                            propertyList.setMarkeringAvPlattformensSlutExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "15_1":
                        testing = propertyList.getAnvandsSomEnDelAvTrappstegfriGangvagExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            anvandsSomEnDelAvTrappstegfriGangvagShowExtraObjects.setText(newString);
                            propertyList.setAnvandsSomEnDelAvTrappstegfriGangvagExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "15_2":
                        testing = propertyList.getBreddPaGangvaggExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            breddPaGangvaggShowExtraObjects.setText(newString);
                            propertyList.setBreddPaGangvaggExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "15_3":
                        testing = propertyList.getLutningExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            lutningShowExtraObjects.setText(newString);
                            propertyList.setLutningExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "15_4":
                        testing = propertyList.getFriPassageForMinstaHjuletPaEnRullstolExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            friPassageForMinstaHjuletPaEnRullstolShowExtraObjects.setText(newString);
                            propertyList.setFriPassageForMinstaHjuletPaEnRullstolExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "15_5":
                        testing = propertyList.getFriPassageOmSakerhetschikanerForekommerExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            friPassageOmSakerhetschikanerForekommerShowExtraObjects.setText(newString);
                            propertyList.setFriPassageOmSakerhetschikanerForekommerExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "15_6":
                        testing = propertyList.getMarkeringAvGangbaneytanExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            markeringAvGangbaneytanShowExtraObjects.setText(newString);
                            propertyList.setMarkeringAvGangbaneytanExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                    case "15_7":
                        testing = propertyList.getSakerPassageExtraObjects();

                        if (testing != null && !testing.isEmpty() && !testing.equals("null")) {

                            aList = new ArrayList(Arrays.asList(testing.split("\n")));
                            indexToRemove = aList.size();

                            aList.remove(indexToRemove - 1);

                            newString = "";

                            for (int j = 0; j < aList.size(); j++) {
                                newString += aList.get(j) + "\n";
                            }
                            sakerPassageShowExtraObjects.setText(newString);
                            propertyList.setSakerPassageExtraObjects(newString);
                            databases.updatePropertyListData(propertyList);
                        }
                        break;
                }

                toast = Toast.makeText(getApplicationContext(),
                        "Objektet är raderat",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        builder.show();
    }

    public void insertValues() {

        switch (rowID) {
            case 1:
                parkeringsmojligheterForFunktionshindrade.setText(propertyList.getParkeringsmojligheterForFunktionshindrade());
                placeringAvParkeringForFunktionshindrade.setText(propertyList.getPlaceringAvParkeringForFunktionshindrade());

                parkeringsmojligheterForFunktionshindradeShowExtraObjects.setText(propertyList.getParkeringsmojligheterForFunktionshindradeExtraObjects());
                placeringAvParkeringForFunktionshindradeShowExtraObjects.setText(propertyList.getPlaceringAvParkeringForFunktionshindradeExtraObjects());

                break;
            case 2:
                forekomstAvHinderfriGangvag.setText(propertyList.getForekomstAvHinderfriGangvag());
                langdenPaHindergriaGangvagar.setText(propertyList.getLangdenPaHindergriaGangvagar());
                reflekterandeEgenskaper.setText(propertyList.getReflekterandeEgenskaper());

                forekomstAvHinderfriGangvagShowExtraObjects.setText(propertyList.getForekomstAvHinderfriGangvagExtraObjects());
                langdenPaHindergriaGangvagarShowExtraObjects.setText(propertyList.getLangdenPaHindergriaGangvagarExtraObjects());
                reflekterandeEgenskaperShowExtraObjects.setText(propertyList.getReflekterandeEgenskaperExtraObjects());
                break;
            case 3:
                hinderfriGangvagsbredd.setText(propertyList.getHinderfriGangvagsbredd());
                trosklarPaHinderfriGangvag.setText(propertyList.getTrosklarPaHinderfriGangvag());

                hinderfriGangvagsbreddShowExtraObjects.setText(propertyList.getHinderfriGangvagsbreddExtraObjects());
                trosklarPaHinderfriGangvagShowExtraObjects.setText(propertyList.getTrosklarPaHinderfriGangvagExtraObjects());
                break;
            case 4:
                trappstegsfriVag.setText(propertyList.getTrappstegsfriVag());
                breddPaTrappor.setText(propertyList.getBreddPaTrappor());
                visuelMarkeringPaForstaOchSistaSteget.setText(propertyList.getVisuelMarkeringPaForstaOchSistaSteget());
                taktilVarningForeForstaUppgaendeTrappsteg.setText(propertyList.getTaktilVarningForeForstaUppgaendeTrappsteg());
                ramperForPersonerMedFunktionsnedsättningar.setText(propertyList.getRamperForPersonerMedFunktionsnedsattningar());
                ledstangerPaBadaSidorOchTvaNivaer.setText(propertyList.getLedstangerPaBadaSidorOchTvaNivaer());
                hissar.setText(propertyList.getHissar());
                rulltrapporOchRullramper.setText(propertyList.getRulltrapporOchRullramper());
                plankorsningar.setText(propertyList.getPlankorsningar());

                trappstegsfriVagShowExtraObjects.setText(propertyList.getTrappstegsfriVagExtraObjects());
                breddPaTrapporShowExtraObjects.setText(propertyList.getBreddPaTrapporExtraObjects());
                visuelMarkeringPaForstaOchSistaStegetShowExtraObjects.setText(propertyList.getVisuelMarkeringPaForstaOchSistaStegetExtraObjects());
                taktilVarningForeForstaUppgaendeTrappstegShowExtraObjects.setText(propertyList.getTaktilVarningForeForstaUppgaendeTrappstegExtraObjects());
                ramperForPersonerMedFunktionsnedsättningarShowExtraObjects.setText(propertyList.getRamperForPersonerMedFunktionsnedsattningarExtraObjects());
                ledstangerPaBadaSidorOchTvaNivaerShowExtraObjects.setText(propertyList.getLedstangerPaBadaSidorOchTvaNivaerExtraObjects());
                hissarShowExtraObjects.setText(propertyList.getHissarExtraObjects());
                rulltrapporOchRullramperShowExtraObjects.setText(propertyList.getRulltrapporOchRullramperExtraObjects());
                plankorsningarShowExtraObjects.setText(propertyList.getPlankorsningarExtraObjects());
                break;
            case 5:
                tydligMarkering.setText(propertyList.getTydligMarkering());
                tillhandahållandeAvInformationTillSynskadade.setText(propertyList.getTillhandahållandeAvInformationTillSynskadade());
                fjarrstyrdaLjudanordningarEllerTeleapplikationer.setText(propertyList.getFjarrstyrdaLjudanordningarEllerTeleapplikationer());
                taktilInformationPaLedstangerEllerVaggar.setText(propertyList.getTaktilInformationPaLedstangerEllerVaggar());

                tydligMarkeringShowExtraObjects.setText(propertyList.getTydligMarkeringExtraObjects());
                tillhandahållandeAvInformationTillSynskadadeShowExtraObjects.setText(propertyList.getTillhandahållandeAvInformationTillSynskadadeExtraObjects());
                fjarrstyrdaLjudanordningarEllerTeleapplikationerShowExtraObjects.setText(propertyList.getFjarrstyrdaLjudanordningarEllerTeleapplikationerExtraObjects());
                taktilInformationPaLedstangerEllerVaggarShowExtraObjects.setText(propertyList.getTaktilInformationPaLedstangerEllerVaggarExtraObjects());
                break;
            case 6:
                halksakerhet.setText(propertyList.getHalksakerhet());
                ojamnheterSomOverstiger.setText(propertyList.getOjamnheterSomOverstiger());

                halksakerhetShowExtraObjects.setText(propertyList.getHalksakerhetExtraObjects());
                ojamnheterSomOverstigerShowExtraObjects.setText(propertyList.getOjamnheterSomOverstigerExtraObjects());
                break;
            case 7:
                glasdorrarEllerGenomskinligaVaggarLangsGangvagar.setText(propertyList.getGlasdorrarEllerGenomskinligaVaggarLangsGangvagar());
                glasdorrarEllerGenomskinligaVaggarLangsGangvagarShowExtraObjects.setText(propertyList.getGlasdorrarEllerGenomskinligaVaggarLangsGangvagarExtraObjects());
                break;
            case 8:
                toalettutrymmeAnpassatForRullstolsburnaPersoner.setText(propertyList.getToalettutrymmeAnpassatForRullstolsburnaPersoner());
                skotplatserTillgangligaForBadeKonen.setText(propertyList.getSkotplatserTillgangligaForBadeKonen());
                toalettutrymmeAnpassatForRullstolsburnaPersonerShowExtraObjects.setText(propertyList.getToalettutrymmeAnpassatForRullstolsburnaPersonerExtraObjects());
                skotplatserTillgangligaForBadeKonenShowExtraObjects.setText(propertyList.getSkotplatserTillgangligaForBadeKonenExtraObjects());
                break;
            case 9:
                kontrastMotBakgrundOchAvrundandeKanter.setText(propertyList.getKontrastMotBakgrundOchAvrundandeKanter());
                placeringAvInredningOchEnheter.setText(propertyList.getPlaceringAvInredningOchEnheter());
                sittmojligheterOchPlatsForEnRullstolsbundenPerson.setText(propertyList.getSittmojligheterOchPlatsForEnRullstolsbundenPerson());
                vaderskyddatOmrade.setText(propertyList.getVaderskyddatOmrade());

                kontrastMotBakgrundOchAvrundandeKanterShowExtraObjects.setText(propertyList.getKontrastMotBakgrundOchAvrundandeKanterExtraObjects());
                placeringAvInredningOchEnheterShowExtraObjects.setText(propertyList.getPlaceringAvInredningOchEnheterExtraObjects());
                sittmojligheterOchPlatsForEnRullstolsbundenPersonShowExtraObjects.setText(propertyList.getSittmojligheterOchPlatsForEnRullstolsbundenPersonExtraObjects());
                vaderskyddatOmradeShowExtraObjects.setText(propertyList.getVaderskyddatOmradeExtraObjects());
                break;
            case 10:
                belysningPaStationensExternaOmraden.setText(propertyList.getBelysningPaStationensExternaOmraden());
                belysningLangsHinderfriaGangvagar.setText(propertyList.getBelysningLangsHinderfriaGangvagar());
                belysningPaPlattformar.setText(propertyList.getBelysningPaPlattformar());
                nodbelysning.setText(propertyList.getNodbelysning());

                belysningPaStationensExternaOmradenShowExtraObjects.setText(propertyList.getBelysningPaStationensExternaOmradenExtraObjects());
                belysningLangsHinderfriaGangvagarShowExtraObjects.setText(propertyList.getBelysningLangsHinderfriaGangvagarExtraObjects());
                belysningPaPlattformarShowExtraObjects.setText(propertyList.getBelysningPaPlattformarExtraObjects());
                nodbelysningShowExtraObjects.setText(propertyList.getNodbelysningExtraObjects());
                break;
            case 11:
                skyltarAvstand.setText(propertyList.getSkyltarAvstand());
                pictogram.setText(propertyList.getPictogram());
                kontrast.setText(propertyList.getKontrast());
                enhetlig.setText(propertyList.getEnhetlig());
                synligIAllaBelysningsforhallanden.setText(propertyList.getSynligIAllaBelysningsforhallanden());
                skyltarEnligtISO.setText(propertyList.getSkyltarEnligtISO());

                skyltarAvstandShowExtraObjects.setText(propertyList.getSkyltarAvstandExtraObjects());
                pictogramShowExtraObjects.setText(propertyList.getPictogramExtraObjects());
                kontrastShowExtraObjects.setText(propertyList.getKontrastExtraObjects());
                enhetligShowExtraObjects.setText(propertyList.getEnhetligExtraObjects());
                synligIAllaBelysningsforhallandenShowExtraObjects.setText(propertyList.getSynligIAllaBelysningsforhallandenExtraObjects());
                skyltarEnligtISOShowExtraObjects.setText(propertyList.getSkyltarEnligtISOExtraObjects());
                break;
            case 12:
                stipaNiva.setText(propertyList.getStipaNiva());
                stipaNivaShowExtraObjects.setText(propertyList.getStipaNivaExtraObjects());

                break;
            case 13:
                forekomstAvRiskomrade.setText(propertyList.getForekomstAvRiskomrade());
                plattformsMinstaBredd.setText(propertyList.getPlattformsMinstaBredd());
                avstandMellanLitetHinder.setText(propertyList.getAvstandMellanLitetHinder());
                avstandMellanStortHinder.setText(propertyList.getAvstandMellanStortHinder());
                markeringAvRiskomradetsGrans.setText(propertyList.getMarkeringAvRiskomradetsGrans());
                breddenPaVarningslinjeOchHalksakerhetOchFarg.setText(propertyList.getBreddenPaVarningslinjeOchHalksakerhetOchFarg());
                materialPaPlattformskanten.setText(propertyList.getMaterialPaPlattformskanten());

                forekomstAvRiskomradeShowExtraObjects.setText(propertyList.getForekomstAvRiskomradeExtraObjects());
                plattformsMinstaBreddShowExtraObjects.setText(propertyList.getPlattformsMinstaBreddExtraObjects());
                avstandMellanLitetHinderShowExtraObjects.setText(propertyList.getAvstandMellanLitetHinderExtraObjects());
                avstandMellanStortHinderShowExtraObjects.setText(propertyList.getAvstandMellanStortHinderExtraObjects());
                markeringAvRiskomradetsGransShowExtraObjects.setText(propertyList.getMarkeringAvRiskomradetsGransExtraObjects());
                breddenPaVarningslinjeOchHalksakerhetOchFargShowExtraObjects.setText(propertyList.getBreddenPaVarningslinjeOchHalksakerhetOchFargExtraObjects());
                materialPaPlattformskantenShowExtraObjects.setText(propertyList.getMaterialPaPlattformskantenExtraObjects());
                break;
            case 14:
                markeringAvPlattformensSlut.setText(propertyList.getMarkeringAvPlattformensSlut());
                markeringAvPlattformensSlutShowExtraObjects.setText(propertyList.getMarkeringAvPlattformensSlutExtraObjects());

                break;
            case 15:
                anvandsSomEnDelAvTrappstegfriGangvag.setText(propertyList.getAnvandsSomEnDelAvTrappstegfriGangvag());
                breddPaGangvagg.setText(propertyList.getBreddPaGangvagg());
                lutning.setText(propertyList.getLutning());
                friPassageForMinstaHjuletPaEnRullstol.setText(propertyList.getFriPassageForMinstaHjuletPaEnRullstol());
                friPassageOmSakerhetschikanerForekommer.setText(propertyList.getFriPassageOmSakerhetschikanerForekommer());
                markeringAvGangbaneytan.setText(propertyList.getMarkeringAvGangbaneytan());
                sakerPassage.setText(propertyList.getSakerPassage());

                anvandsSomEnDelAvTrappstegfriGangvagShowExtraObjects.setText(propertyList.getAnvandsSomEnDelAvTrappstegfriGangvagExtraObjects());
                breddPaGangvaggShowExtraObjects.setText(propertyList.getBreddPaGangvaggExtraObjects());
                lutningShowExtraObjects.setText(propertyList.getLutningExtraObjects());
                friPassageForMinstaHjuletPaEnRullstolShowExtraObjects.setText(propertyList.getFriPassageForMinstaHjuletPaEnRullstolExtraObjects());
                friPassageOmSakerhetschikanerForekommerShowExtraObjects.setText(propertyList.getFriPassageOmSakerhetschikanerForekommerExtraObjects());
                markeringAvGangbaneytanShowExtraObjects.setText(propertyList.getMarkeringAvGangbaneytanExtraObjects());
                sakerPassageShowExtraObjects.setText(propertyList.getSakerPassageExtraObjects());
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_for_edit_object, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent resultIntent;

        switch (item.getItemId()) {
            case R.id.save_edite_check:

                switch (rowID) {
                    case 1:
                        propertyList.setParkeringsmojligheterForFunktionshindrade(String.valueOf(parkeringsmojligheterForFunktionshindrade.getText()));
                        propertyList.setPlaceringAvParkeringForFunktionshindrade(String.valueOf(placeringAvParkeringForFunktionshindrade.getText()));
                        break;
                    case 2:
                        propertyList.setForekomstAvHinderfriGangvag(String.valueOf(forekomstAvHinderfriGangvag.getText()));
                        propertyList.setLangdenPaHindergriaGangvagar(String.valueOf(langdenPaHindergriaGangvagar.getText()));
                        propertyList.setReflekterandeEgenskaper(String.valueOf(reflekterandeEgenskaper.getText()));
                        break;
                    case 3:
                        propertyList.setHinderfriGangvagsbredd(String.valueOf(hinderfriGangvagsbredd.getText()));
                        propertyList.setTrosklarPaHinderfriGangvag(String.valueOf(trosklarPaHinderfriGangvag.getText()));
                        break;
                    case 4:
                        propertyList.setTrappstegsfriVag(String.valueOf(trappstegsfriVag.getText()));
                        propertyList.setBreddPaTrappor(String.valueOf(breddPaTrappor.getText()));
                        propertyList.setVisuelMarkeringPaForstaOchSistaSteget(String.valueOf(visuelMarkeringPaForstaOchSistaSteget.getText()));
                        propertyList.setTaktilVarningForeForstaUppgaendeTrappsteg(String.valueOf(taktilVarningForeForstaUppgaendeTrappsteg.getText()));
                        propertyList.setRamperForPersonerMedFunktionsnedsattningar(String.valueOf(ramperForPersonerMedFunktionsnedsättningar.getText()));
                        propertyList.setLedstangerPaBadaSidorOchTvaNivaer(String.valueOf(ledstangerPaBadaSidorOchTvaNivaer.getText()));
                        propertyList.setHissar(String.valueOf(hissar.getText()));
                        propertyList.setRulltrapporOchRullramper(String.valueOf(rulltrapporOchRullramper.getText()));
                        propertyList.setPlankorsningar(String.valueOf(plankorsningar.getText()));
                        break;
                    case 5:
                        propertyList.setTydligMarkering(String.valueOf(tydligMarkering.getText()));
                        propertyList.setTillhandahållandeAvInformationTillSynskadade(String.valueOf(tillhandahållandeAvInformationTillSynskadade.getText()));
                        propertyList.setFjarrstyrdaLjudanordningarEllerTeleapplikationer(String.valueOf(fjarrstyrdaLjudanordningarEllerTeleapplikationer.getText()));
                        propertyList.setTaktilInformationPaLedstangerEllerVaggar(String.valueOf(taktilInformationPaLedstangerEllerVaggar.getText()));
                        break;
                    case 6:
                        propertyList.setHalksakerhet(String.valueOf(halksakerhet.getText()));
                        propertyList.setOjamnheterSomOverstiger(String.valueOf(ojamnheterSomOverstiger.getText()));
                        break;
                    case 7:
                        propertyList.setGlasdorrarEllerGenomskinligaVaggarLangsGangvagar(String.valueOf(glasdorrarEllerGenomskinligaVaggarLangsGangvagar.getText()));
                        break;
                    case 8:
                        propertyList.setToalettutrymmeAnpassatForRullstolsburnaPersoner(String.valueOf(toalettutrymmeAnpassatForRullstolsburnaPersoner.getText()));
                        propertyList.setSkotplatserTillgangligaForBadeKonen(String.valueOf(skotplatserTillgangligaForBadeKonen.getText()));
                        break;
                    case 9:
                        propertyList.setKontrastMotBakgrundOchAvrundandeKanter(String.valueOf(kontrastMotBakgrundOchAvrundandeKanter.getText()));
                        propertyList.setPlaceringAvInredningOchEnheter(String.valueOf(placeringAvInredningOchEnheter.getText()));
                        propertyList.setSittmojligheterOchPlatsForEnRullstolsbundenPerson(String.valueOf(sittmojligheterOchPlatsForEnRullstolsbundenPerson.getText()));
                        propertyList.setVaderskyddatOmrade(String.valueOf(vaderskyddatOmrade.getText()));
                        break;
                    case 10:
                        propertyList.setBelysningPaStationensExternaOmraden(String.valueOf(belysningPaStationensExternaOmraden.getText()));
                        propertyList.setBelysningLangsHinderfriaGangvagar(String.valueOf(belysningLangsHinderfriaGangvagar.getText()));
                        propertyList.setBelysningPaPlattformar(String.valueOf(belysningPaPlattformar.getText()));
                        propertyList.setNodbelysning(String.valueOf(nodbelysning.getText()));
                        break;
                    case 11:
                        propertyList.setSkyltarAvstand(String.valueOf(skyltarAvstand.getText()));
                        propertyList.setPictogram(String.valueOf(pictogram.getText()));
                        propertyList.setKontrast(String.valueOf(kontrast.getText()));
                        propertyList.setEnhetlig(String.valueOf(enhetlig.getText()));
                        propertyList.setSynligIAllaBelysningsforhallanden(String.valueOf(synligIAllaBelysningsforhallanden.getText()));
                        propertyList.setSkyltarEnligtISO(String.valueOf(skyltarEnligtISO.getText()));
                        break;
                    case 12:
                        propertyList.setStipaNiva(String.valueOf(stipaNiva.getText()));
                        break;
                    case 13:
                        propertyList.setForekomstAvRiskomrade(String.valueOf(forekomstAvRiskomrade.getText()));
                        propertyList.setPlattformsMinstaBredd(String.valueOf(plattformsMinstaBredd.getText()));
                        propertyList.setAvstandMellanLitetHinder(String.valueOf(avstandMellanLitetHinder.getText()));
                        propertyList.setAvstandMellanStortHinder(String.valueOf(avstandMellanStortHinder.getText()));
                        propertyList.setMarkeringAvRiskomradetsGrans(String.valueOf(markeringAvRiskomradetsGrans.getText()));
                        propertyList.setBreddenPaVarningslinjeOchHalksakerhetOchFarg(String.valueOf(breddenPaVarningslinjeOchHalksakerhetOchFarg.getText()));
                        propertyList.setMaterialPaPlattformskanten(String.valueOf(materialPaPlattformskanten.getText()));
                        break;
                    case 14:
                        propertyList.setMarkeringAvPlattformensSlut(String.valueOf(markeringAvPlattformensSlut.getText()));
                        break;
                    case 15:
                        propertyList.setAnvandsSomEnDelAvTrappstegfriGangvag(String.valueOf(anvandsSomEnDelAvTrappstegfriGangvag.getText()));
                        propertyList.setBreddPaGangvagg(String.valueOf(breddPaGangvagg.getText()));
                        propertyList.setLutning(String.valueOf(lutning.getText()));
                        propertyList.setFriPassageForMinstaHjuletPaEnRullstol(String.valueOf(friPassageForMinstaHjuletPaEnRullstol.getText()));
                        propertyList.setFriPassageOmSakerhetschikanerForekommer(String.valueOf(friPassageOmSakerhetschikanerForekommer.getText()));
                        propertyList.setMarkeringAvGangbaneytan(String.valueOf(markeringAvGangbaneytan.getText()));
                        propertyList.setSakerPassage(String.valueOf(sakerPassage.getText()));
                        break;
                }

                databases.updatePropertyListData(propertyList);
                resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                return true;

            case android.R.id.home:
                resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

