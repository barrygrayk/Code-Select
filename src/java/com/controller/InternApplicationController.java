package com.controller;

import com.Messages.InventoryAlerts;
import com.applicants.Model.Applicant;
import com.db.connection.InternAplicationTableConnection;
import com.validation.MrKaplan;
import com.validation.TheEqualizer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Barry Gray
 */
@ManagedBean(name = "Apply", eager = true)
@SessionScoped
public class InternApplicationController extends Applicant implements Serializable {

    private String country;
    private Applicant selectedApplicant;
    private TreeMap<String, String> countries = new TreeMap();
    private String code = "";
    private List<Applicant> applicants = new ArrayList<>();

    public InternApplicationController() {
        super();
    }

    public Applicant getSelectedApplicant() {
        return selectedApplicant;
    }

    public void setSelectedApplicant(Applicant selectedApplicant) {
        this.selectedApplicant = selectedApplicant;
    }

    @PostConstruct
    public void init() {
        countries.put("Algeria", "213");
        countries.put("Andorra", "376");
        countries.put("Angola", "244");
        countries.put("Anguilla", "1264");
        countries.put("Antigua & Barbuda", "1268");
        countries.put("Argentina", "54");
        countries.put("Armenia", "374");
        countries.put("Aruba", "297");
        countries.put("Australia", "61");
        countries.put("Austria", " 43");
        countries.put("Azerbaijan", " 994");
        countries.put("Bahamas", " 1242");
        countries.put("Bangladesh", "880");
        countries.put("Barbados", " 1246");
        countries.put("Belarus", " 375");
        countries.put("Belgium", " 32");
        countries.put("Belize", " 501");
        countries.put("Benin", " 229");
        countries.put("Bermuda", " 1441");
        countries.put("Bhutan", " 975");
        countries.put("Bolivia", " 591");
        countries.put("Bosnia Herzegovina", " 387");
        countries.put("Botswana", " 267");
        countries.put("Brazil", " 55");
        countries.put("Brunei", " 673");
        countries.put("Bulgaria", " 359");
        countries.put("Burkina Faso", " 226");
        countries.put("Burundi", " 257");
        countries.put("Cambodia", " 855");
        countries.put("Cameroon", " 237");
        countries.put("Canada", " 1");
        countries.put("Cape Verde Islands", " 238");
        countries.put("Cayman Islands", " 1345");
        countries.put("Central African Republic", " 236");
        countries.put("Chile", " 56");
        countries.put("China", " 86");
        countries.put("Colombia", " 57");
        countries.put("Comoros", " 269");
        countries.put("Congo", " 242");
        countries.put("Cook Islands", " 682");
        countries.put("Costa Rica", " 506");
        countries.put("Croatia", " 385");
        countries.put("Cuba", " 53");
        countries.put("Cyprus North", " 90392");
        countries.put("Cyprus South", " 357");
        countries.put("Czech Republic", " 42");
        countries.put("Denmark", " 45");
        countries.put("Djibouti", " 253");
        countries.put("Dominica", " 1809");
        countries.put("Dominican Republic", " 1809");
        countries.put("Ecuador", " 593");
        countries.put("Egypt", " 20");
        countries.put("El Salvador", " 503");
        countries.put("Equatorial Guinea", " 240");
        countries.put("Eritrea", " 291");
        countries.put("Estonia", " 372");
        countries.put("Ethiopia", " 251");
        countries.put("Falkland Islands", " 500");
        countries.put("Faroe Islands", " 298");
        countries.put("Fiji", " 679");
        countries.put("Finland", " 358");
        countries.put("France", " 33");
        countries.put("French Guiana", " 594");
        countries.put("French Polynesia", " 689");
        countries.put("Gabon", " 241");
        countries.put("Gambia", " 220");
        countries.put("Georgia", " 7880");
        countries.put("Germany", " 49");
        countries.put("Ghana", " 233");
        countries.put("Gibraltar", " 350");
        countries.put("Greece", " 30");
        countries.put("Greenland", " 299");
        countries.put("Grenada", " 1473");
        countries.put("Guadeloupe", " 590");
        countries.put("Guam", " 671");
        countries.put("Guatemala", " 502");
        countries.put("Guinea", " 224");
        countries.put("Guinea - Bissau", " 245");
        countries.put("Guyana", " 592");
        countries.put("Haiti", " 509");
        countries.put("Honduras", " 504");
        countries.put("Hong Kong", " 852");
        countries.put("Hungary", " 36");
        countries.put("Iceland", " 354");
        countries.put("India", " 91");
        countries.put("Indonesia", " 62");
        countries.put("Iran", " 98");
        countries.put("Iraq", " 964");
        countries.put("Ireland", " 353");
        countries.put("Israel", " 972");
        countries.put("Italy", " 39");
        countries.put("Jamaica", " 1876");
        countries.put("Japan", " 81");
        countries.put("Jordan", " 962");
        countries.put("Kazakhstan", " 7");
        countries.put("Kenya", " 254");
        countries.put("Kiribati", " 686");
        countries.put("Korea North", " 850");
        countries.put("Korea South", " 82");
        countries.put("Kuwait", " 965");
        countries.put("Kyrgyzstan", " 996");
        countries.put("Laos", " 856");
        countries.put("Latvia", " 371");
        countries.put("Lebanon", " 961");
        countries.put("Lesotho", " 266");
        countries.put("Liberia", " 231");
        countries.put("Libya", " 218");
        countries.put("Liechtenstein", " 417");
        countries.put("Lithuania", " 370");
        countries.put("Luxembourg", " 352");
        countries.put("Macao", " 853");
        countries.put("Macedonia", " 389");
        countries.put("Madagascar", " 261");
        countries.put("Malawi", " 265");
        countries.put("Malaysia", " 60");
        countries.put("Maldives", " 960");
        countries.put("Mali", " 223");
        countries.put("Malta", " 356");
        countries.put("Marshall Islands", " 692");
        countries.put("Martinique", " 596");
        countries.put("Mauritania", " 222");
        countries.put("Mayotte", " 269");
        countries.put("Mexico", " 52");
        countries.put("Micronesia", " 691");
        countries.put("Moldova", " 373");
        countries.put("Monaco", " 377");
        countries.put("Mongolia", " 976");
        countries.put("Montserrat", " 1664");
        countries.put("Morocco", " 212");
        countries.put("Mozambique", " 258");
        countries.put("Myanmar", " 95");
        countries.put("Namibia", " 264");
        countries.put("Nauru", " 674");
        countries.put("Nepal", " 977");
        countries.put("Netherlands", " 31");
        countries.put("New Caledonia", " 687");
        countries.put("New Zealand", " 64");
        countries.put("Nicaragua", " 505");
        countries.put("Niger", " 227");
        countries.put("Nigeria", " 234");
        countries.put("Niue", " 683");
        countries.put("Norfolk Islands", " 672");
        countries.put("Northern Marianas", " 670");
        countries.put("Norway", " 47");
        countries.put("Oman", " 968");
        countries.put("Palau", " 680");
        countries.put("Panama", " 507");
        countries.put("Papua New Guinea", " 675");
        countries.put("Paraguay", " 595");
        countries.put("Peru", " 51");
        countries.put("Philippines", " 63");
        countries.put("Poland", " 48");
        countries.put("Portugal", " 351");
        countries.put("Puerto Rico", " 1787");
        countries.put("Qatar", " 974");
        countries.put("Reunion", " 262");
        countries.put("Romania", " 40");
        countries.put("Russia", " 7");
        countries.put("Rwanda", " 250");
        countries.put("San Marino", " 378");
        countries.put("Sao Tome & Principe", " 239");
        countries.put("Saudi Arabia", " 966");
        countries.put("Senegal", " 221");
        countries.put("Serbia", " 381");
        countries.put("Seychelles", " 248");
        countries.put("Sierra Leone", " 232");
        countries.put("Singapore", " 65");
        countries.put("Slovak Republic", " 421");
        countries.put("Slovenia", " 386");
        countries.put("Solomon Islands", " 677");
        countries.put("Somalia", " 252");
        countries.put("South Africa", " 27");
        countries.put("Spain", " 34");
        countries.put("Sri Lanka", " 94");
        countries.put("St. Helena", " 290");
        countries.put("St. Kitts", " 1869");
        countries.put("St. Lucia", " 1758");
        countries.put("Sudan", " 249");
        countries.put("Suriname", " 597");
        countries.put("Swaziland", " 268");
        countries.put("Sweden", " 46");
        countries.put("Switzerland", " 41");
        countries.put("Syria", " 963");
        countries.put("Taiwan", " 886");
        countries.put("Tajikstan", " 7");
        countries.put("Thailand", "66");
        countries.put("Togo", " 228");
        countries.put("Tonga", " 676");
        countries.put("Trinidad & Tobago", " 1868");
        countries.put("Tunisia", " 216");
        countries.put("Turkey", " 90");
        countries.put("Turkmenistan", " 7");
        countries.put("Turkmenistan", " 993");
        countries.put("Turks & Caicos Islands", " 1649");
        countries.put("Tuvalu", " 688");
        countries.put("Uganda", " 256");
        countries.put("UK", " 44");
        countries.put("Ukraine", " 380");
        countries.put("United Arab Emirates", " 971");
        countries.put("Uruguay", " 598");
        countries.put("USA", "1");
        countries.put("Uzbekistan", "7");
        countries.put("Vanuatu", "678");
        countries.put("Vatican City", "379");
        countries.put("Venezuela", "58");
        countries.put("Vietnam", "84");
        countries.put("Virgin Islands - British", "1284");
        countries.put("Virgin Islands - US", "1340");
        countries.put("Wallis & Futuna", "681");
        countries.put("Yemen North", "969");
        countries.put("Yemen South", "967");
        countries.put("Zambia", "260");
        countries.put("Zimbabwe", "263");
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Map<String, String> getCountries() {
        return countries;
    }

    public void setCountries(TreeMap<String, String> countries) {
        this.countries = countries;
    }

    public void onCountryChange() {
        if (country != null) {
            code = "+" + country;
        } else {
            code = "";
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void onRowSelect(SelectEvent e) {
        System.err.println("------------");
        selectedApplicant = (Applicant) e.getObject();
        System.out.println(selectedApplicant.getMotivationForApllication());

    }

    public void onRowUnselect(UnselectEvent e) {
        selectedApplicant = null;
    }

    public List<Applicant> getApplicants() {
        applicants = new InternAplicationTableConnection().getApplicants();
        return applicants;
    }

    public void setApplicants(List<Applicant> applicants) {
        this.applicants = applicants;
    }

    public void sendRequst() {
        System.out.println("Here");
        MrKaplan validate = new MrKaplan();
        TheEqualizer sanitise = new TheEqualizer();
        if (validate.isValidInput(getFirstname()) && validate.isValidInput(getLastname())) {
            Applicant application = new Applicant();
            application.setFirstname(sanitise.toUperAndLower(getFirstname()));
            application.setLastname(sanitise.toUperAndLower(getLastname()));
            application.setEmailAddress(getEmailAddress());
            application.setPhoneNumber(code);
            application.setMotivationForApllication(getMotivationForApllication());
            System.out.println("-----cont---------");
            new InternAplicationTableConnection().sendApllicationRequest(application);
            System.out.println("-----Cont--Done-------");
        }
    }
    
    public void acceptRequst (){
        new InternAplicationTableConnection().sendAcceRequest(selectedApplicant);
    }

}