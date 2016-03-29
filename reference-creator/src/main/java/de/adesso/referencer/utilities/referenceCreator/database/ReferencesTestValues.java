/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.utilities.referenceCreator.database;

import de.adesso.referencer.utilities.referenceCreator.Entities.Raw.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author odzhara-ongom
 */
public class ReferencesTestValues {

    private Random random = new Random();

    private String[] peopleNames = {
        "Ägidius",
        "Aaron",
        "Abraham",
        "Achaz",
        "Achim",
        "Adalbert",
        "Adam",
        "Adelbert",
        "Adelfried"
    };

    private String[] peopleLastNames = {
        "Müller",
        "Schmidt",
        "Schneider",
        "Fischer",
        "Weber",
        "Meyer",
        "Wagner",
        "Becker",
        "Schulz",
        "Hoffmann",
        "Schäfer",
        "Bauer",
        "Koch",
        "Richter"
    };

    private String[] cities = {
        "Berlin", "Hamburg", "Dresden", "Köln", "München"
    };
    private String[] streets = {
        "Brückenstraße", "Cäcilienstraße", "Ehrenstraße",
        "Filzengraben", "Gereonstraße", "Hahnenstraße", "Hohe Straße"
    };
    private String[] emailDomains = {
        "@mail.ru", "@gmail.com", "@yahoo.com", "@web.de"
    };

    private String[] clientNames = {
        "Volkswagen AG", "Daimler AG", "E.ON SE", "BMW AG", "Schwarz Beteiligungs GmbH",
        "BASF SE", "Siemens AG", "Metro AG", "Deutsche Telekom AG", "Lidl Stiftung & Co. KG",
        "Deutsche Post DHL Group", "Audi AG", "Rewe Group", "Robert Bosch GmbH", "RWE AG"
    };

    private String[] clientDescription = {
        "Die Volkswagen Aktiengesellschaft (abgekürzt VW AG) mit Sitz in Wolfsburg ist der größte europäische Automobilhersteller und mit Toyota und General Motors einer der größten weltweit.[3][4] Die VW AG agiert als Muttergesellschaft der Fahrzeugmarken Volkswagen Pkw, Audi, Seat und Škoda sowie der Premiummarken Bentley, Bugatti, Ducati (Motorräder), Lamborghini und Porsche.[5] 2007 bis 2011 erweiterte der Konzern auch seine Nutzfahrzeugsparte (Lkw und Busse) mit der Marke Volkswagen Nutzfahrzeuge um die Unternehmen MAN und Scania.",
        "Die Daimler AG mit Sitz in Stuttgart ist ein börsennotierter Hersteller von Personenkraftwagen und Nutzfahrzeugen. Ihre bekannteste Marke ist Mercedes-Benz. Das Unternehmen ist außerdem Anbieter von Finanzdienstleistungen.",
        "Die börsennotierte E.ON SE (von englisch eon ‚Äon‘)[3] mit Hauptsitz in Düsseldorf ist der größte deutsche Energiekonzern und hauptsächlich im europäischen Gas- und Elektrizitätsgeschäft tätig.\n"
        + "\n"
        + "Der Energiekonzern gab am 27. April 2015 den Umzug des Firmensitzes mit den Kernsparten erneuerbare Energie, Netze und Kundendienstleistungen nach Essen bekannt. Außerdem wurde die Spaltung des Unternehmens mit der neu zu gründenden Gesellschaft Uniper für die Sparte Stromerzeugung (mit den Kern- und Kohlekraftwerken) und Energiehandel sowie Exploration mit Sitz in Düsseldorf mitgeteilt. E.ON behält dabei 40.000 Mitarbeiter und die neue Uniper 15.000 Mitarbeiter. Vorstandschef von Uniper wird der bisherige E.ON-Finanzvorstand Klaus Schäfer.[4]",
        "Die Bayerische Motoren Werke Aktiengesellschaft (BMW AG) ist die Muttergesellschaft der BMW Group, einem weltweit operierenden Automobil- und Motorradhersteller mit Sitz in München. Die Produktpalette umfasst die Automobil- und Motorrad-Marke BMW, die Automarken Mini und Rolls-Royce sowie die BMW-Submarken BMW M und BMW i.",
        "Die Schwarz Beteiligungs GmbH, der die Unternehmen Lidl und Kaufland gehören, ist der größte Handelskonzern Europas.\n"
        + "\n"
        + "Die Schwarz-Gruppe erzielte im Geschäftsjahr 2014/2015 einen Umsatz von 79,3 Milliarden Euro und lag damit im Umsatz vor dem Rivalen Metro. Eigentümer der Schwarz Beteiligungs GmbH sind die Dieter Schwarz Stiftung gGmbH (99,9 % der Anteile) und die Schwarz Unternehmenstreuhand KG (0,1 % der Anteile); letztere hält 100 % der Stimmrechte.[3] Der Sitz der Schwarz-Gruppe ist Neckarsulm.[4] Die Schwarz-Gruppe hatte im Jahr 2010 weltweit über 10.000 Filialen (rund 9000 Lidl-Filialen, davon 3200 in Deutschland, und rund 1000 Kaufland-Filialen, davon 580 in Deutschland).[5] Die Gruppe hat nach eigenen Angaben Filialen in 20 Staaten. Der Online-Handel wird von der Lidl E-Commerce International (ehemals Schwarz E-Commerce), einer Schwestergesellschaft von Lidl, betrieben.[6] Im Ranking der 500 größten Familienunternehmen der Zeitschrift Wirtschaftsblatt nimmt die Schwarz-Gruppe den ersten Platz ein.[7]",
        "Die BASF SE (ehemals „Badische Anilin- & Soda-Fabrik“) ist der nach Umsatz und Marktkapitalisierung weltweit größte Chemiekonzern. Weltweit sind etwa 113.000 Mitarbeiter in mehr als 80 Ländern bei der BASF beschäftigt. Die BASF betreibt über 390 Produktionsstandorte weltweit, ihr Hauptsitz befindet sich in Ludwigshafen am Rhein. 2014 erzielte das Unternehmen bei einem Umsatz von 74,3 Mrd. EUR ein EBIT von 7,4 Mrd. EUR. Die Aktie des Unternehmens ist im DAX an der Frankfurter Wertpapierbörse gelistet und wird ebenfalls an den Börsen in London und Zürich gehandelt.",
        "Die Siemens Aktiengesellschaft ist ein integrierter Technologiekonzern mit den vier Hauptgeschäftsfeldern Energie, Medizintechnik, Industrie sowie Infrastruktur und Städte. Als Telegraphen Bau-Anstalt von Siemens & Halske 1847 in Berlin von Werner Siemens (ab 1888: von Siemens) und Johann Georg Halske gegründet, ist der heutige Siemens-Konzern in 190 Ländern vertreten und zählt weltweit zu den größten Unternehmen der Elektrotechnik und Elektronik. Die Aktiengesellschaft mit Doppelsitz in Berlin und München unterhält 125 Standorte in Deutschland und ist im DAX an der Frankfurter Wertpapierbörse notiert.",
        "Die Metro Group, früher Metro AG, ist die Dachgesellschaft mehrerer Großhandels- und Einzelhandelsunternehmen. Die Gruppe mit Sitz in Düsseldorf beschäftigt knapp 300.000 Mitarbeiter, davon 110.000 in Deutschland (Stand: Dezember 2011[3]). Mit ihren Vertriebsmarken ist sie bisher in 33 Ländern aktiv.",
        "Die Deutsche Telekom AG ist eines der größten europäischen Telekommunikationsunternehmen.[2] Ihr Sitz ist in Bonn.\n"
        + "\n"
        + "Das Unternehmen betreibt technische Netze (ISDN, xDSL, Satelliten, Gigabit-Ethernet, ATM, 2G, 3G, 4G usw.) für den Betrieb von Informations- und Kommunikationsdiensten (IuK), etwa Telefonen (Festnetz und Mobilfunk) oder Onlinediensten.",
        "Lidl ist der größte Discounter in Europa und nach Aldi der zweitgrößte weltweit. Lidl betreibt in 26 europäischen Ländern ca. 9.900 Filialen.[4] Lidl ist Teil der Schwarz-Gruppe mit Sitz in Neckarsulm.",
        "Die Deutsche Post AG mit Sitz in Bonn ist das größte Logistik- und Postunternehmen der Welt. Unter dem Namen Deutsche Post DHL Group tritt der Konzern seit dem 11. März 2015 in der Öffentlichkeit auf. Das Unternehmen entstand 1995 durch Privatisierung der früheren Behörde Deutsche Bundespost und ist seit 2000 Bestandteil des deutschen Leitindexes DAX an der Frankfurter Wertpapierbörse. Mit Wirkung vom 23. September 2013 zog die Deutsche Post in den EURO STOXX 50 ein.[3]",
        "Die Audi AG (Eigenschreibweise: AUDI AG) mit Sitz in Ingolstadt in Bayern ist ein deutscher Automobilhersteller, der dem Volkswagen-Konzern angehört.\n"
        + "\n"
        + "Der Markenname ist ein Wortspiel zur Umgehung der Namensrechte des ehemaligen Kraftfahrzeugherstellers A. Horch & Cie. Motorwagenwerke Zwickau. Unternehmensgründer August Horch, der „seine“ Firma nach Zerwürfnissen mit dem Finanzvorstand verlassen hatte, suchte einen Namen für sein neues Unternehmen und fand ihn im Vorschlag eines Zwickauer Gymnasiasten, der Horch ins Lateinische übersetzte.[2] Audi ist der Imperativ Singular von audire (zu Deutsch hören, zuhören) und bedeutet „Höre!“ oder eben „Horch!“. Am 25. April 1910 wurde die Audi Automobilwerke GmbH Zwickau in das Handelsregister der Stadt Zwickau eingetragen.",
        "Die Rewe Group, eigene Schreibweise REWE Group, ist ein deutscher Handels- und Touristikkonzern mit Sitz in Köln. Das Akronym steht für „Revisionsverband der Westkauf-Genossenschaften“. Unternehmensschwerpunkte bilden der Lebensmittelhandel und die Touristiksparte. Die bedeutendsten Unternehmen unter dem Dach der Rewe Group sind die REWE-Zentral-AG sowie die REWE-Zentralfinanz eG in Köln.",
        "Die Robert Bosch GmbH ist ein im Jahr 1886 von Robert Bosch gegründetes deutsches Unternehmen. Es ist tätig als Automobilzulieferer, Hersteller von Gebrauchsgütern (Elektrowerkzeuge, Haushaltsgeräte) und Industrie- und Gebäudetechnik (Sicherheitstechnik) und darüber hinaus in der automatisierten Verpackungstechnik, wo Bosch den ersten Platz einnimmt.",
        "Die RWE AG (bis 1990 Rheinisch-Westfälisches Elektrizitätswerk AG) mit Sitz in Essen ist ein börsennotierter Energieversorgungskonzern. Am Umsatz gemessen ist er der zweitgrößte Versorger Deutschlands. Der Konzern gehört auch in den Niederlanden seit der Übernahme von Essent zu den führenden Energieversorgern und ist auch in anderen Märkten (z. B. Großbritannien, Belgien, Österreich, Osteuropa, Türkei) vertreten."
    };

    private String[] projectNames = {
        "Entwicklung des Web - Shop", "Patienten Management System International",
        "Medical Guideline Framework", "CRM Support", "Business Server", "Entwicklungsunterstützung",
        "Performence-Optimierung", "Testautomatisierung", "Mobile Website", "Agiles Coaching zur Produktentwicklung",
        "Anforderungsanalyse und JEE-Softwareentwicklung"
    };

    private String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum";

    private String[] projectDecsriptions = {
        "Der Bereich Webshop-Entwicklung umfasst einen wesentlichen Teil des komplexen E-Commerce Ökosystems. Ausschlaggebend für den Erfolg eines Webshops ist unter anderem ein hohes Maß an Usability. Erzielt wird dies durch übersichtliche und performante Shopseiten, kurze und effiziente Bestellstrecken sowie durch die optionale Integration Ihrer bestehenden Systeme (Warenwirtschaft, POS, etc. ). Das Ergebnis: Zufriedene KundInnen zum einen sowie hohe Conversion-Raten, Stabilität und eine perfekte Skalierbarkeit zum anderen. ",
        "Patientendatenmanagementsysteme, kurz PDMS, sind in der Medizininformatik computerisierte Informationssysteme, die in Krankenhäusern die patientenbezogenen Informationen erfassen und darstellen.",
        "Clinical guidelines are systematically developed statements designed to help practitioners and patients decide on appropriate healthcare for specific clinical conditions and/or circumstances.\n"
        + "Good guidelines can change clinical practice and influence patient outcome.\n"
        + "The way in which guidelines are developed, implemented and monitored, influences the likelihood that they will be followed.",};

    public String[] getProjectNames() {
        return projectNames;
    }

    public void setProjectNames(String[] projectNames) {
        this.projectNames = projectNames;
    }

    private String getRandomString(String[] strArray) {
        return strArray[random.nextInt(strArray.length)];
    }

    public String createRandomPersonName() {
        return getRandomString(peopleNames) + " " + getRandomString(peopleLastNames);
    }

    public String createRandomTel() {
        return "+49-221-" + 100000 + random.nextInt(100000);
    }

    public PersonAddress createRandomPersonAdress() {
        PersonAddress result = new PersonAddress();
        result.setCity(getRandomString(cities));
        result.setPlz(50000 + random.nextInt(999) + "");
        result.setCountry("Deutschland");
        result.setStreet(getRandomString(streets) + " " + random.nextInt(99));
        result.setDescription("Random created address");
        return result;
    }

    public Person createRandomPerson() {
        Person result = new Person();
        result.setName(getRandomString(peopleLastNames));
        result.setForename(getRandomString(peopleNames));
        result.setAddress(new ArrayList<PersonAddress>());
        result.getAddress().add(createRandomPersonAdress());
        result.setEmail(new ArrayList<>());
        result.getEmail().add(result.getForename() + "." + result.getName() + getRandomString(emailDomains));
        result.setTel(new ArrayList<>());
        result.getTel().add(createRandomTel());
        result.setDescription("Random created person");
        return result;
    }

    public Person createRandomAdessoPerson() {
        Person result = createRandomPerson();
        result.setEmail(new ArrayList<>());
        result.getEmail().add(result.getForename() + "." + result.getName() + "@adesso.de");
        return result;
    }

    public String getRandomProjectName() {
        return getRandomString(projectNames);
    }

    public String getRandomClientName() {
        return getRandomString(clientNames);
    }

    public List<AdessoClient> getClientsList() {
        List<AdessoClient> result = new ArrayList<>();
        for (int i = 0; i < clientNames.length; i++) {
            result.add(new AdessoClient(clientNames[i], clientDescription[i]));
        }
        return result;
    }

    public String getLoremIpsum() {
        return loremIpsum;
    }

}
