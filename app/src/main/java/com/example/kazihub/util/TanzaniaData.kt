package com.example.kazihub.util

import com.example.kazihub.data.model.JobListing

object TanzaniaData {
    val REGIONS = listOf(
        "Zote (All Tanzania)",
        "Dar es Salaam",
        "Arusha",
        "Dodoma",
        "Mwanza",
        "Mbeya",
        "Zanzibar",
        "Morogoro",
        "Tanga",
        "Kilimanjaro"
    )

    val CATEGORIES = listOf(
        "Zote (All)",
        "Fundi Umeme (Electrical)",
        "Fundi Bomba (Plumbing)",
        "Ujenzi & Useremala (Construction)",
        "Teknolojia & Kompyuta (IT & Tech)",
        "Udereva & Usafiri (Driving)",
        "Ushonaji & Mitindo (Tailoring)",
        "Wapishi & Hotelini (Chefs & Hospitality)",
        "Uhasibu & Mauzo (Accounting & Sales)",
        "Saluni & Urembo (Beauty & Salon)",
        "Kilimo & Mifugo (Agribusiness)"
    )

    val SKILL_CATALOG = mapOf(
        "Fundi Umeme (Electrical)" to listOf(
            "Wiring ya Nyumba", "Solar Power Installation", "CCTV Camera Setup",
            "Generators Repair", "Electric Fence", "Air Conditioner (AC) Repair"
        ),
        "Fundi Bomba (Plumbing)" to listOf(
            "Mabomba ya Maji", "Water Heater Repair", "Kuzibua Chemba",
            "Mifumo ya Matanki", "Pressure Pumps", "Vyoo vya Kisasa"
        ),
        "Ujenzi & Useremala (Construction)" to listOf(
            "Kupiga Rangi (Painting)", "Gypsum & Ceiling", "Kuweka Tiles",
            "Kupaua na Mabati", "Kutengeneza Samani/Meza", "Kujenga Matofali"
        ),
        "Teknolojia & Kompyuta (IT & Tech)" to listOf(
            "Mobile App Development", "Website Design & WordPress", "Graphic Design & Canva",
            "Social Media Marketing", "Computer Maintenance & Software", "Data Entry"
        ),
        "Udereva & Usafiri (Driving)" to listOf(
            "Dereva wa Gari Binafsi", "Dereva wa Malori (Trucks)", "Dereva wa Bajaj",
            "Ufundi wa Magari (Mechanic)", "Kusafirisha Vifurushi (Delivery)", "Leseni Daraja C/E"
        ),
        "Ushonaji & Mitindo (Tailoring)" to listOf(
            "Kushona Vitenge vya Kike", "Kushona Suti za Kiume", "Magauni ya Harusi",
            "Mabegi na Viatu vya Ngozi", "Uniform za Shule na Makampuni", "Kudarizi (Embroidery)"
        ),
        "Wapishi & Hotelini (Chefs & Hospitality)" to listOf(
            "Mpishi wa Keki na Bites", "Mpishi wa Chakula cha Asili", "Barista & Kahawa",
            "Mpishi wa Sherehe (Catering)", "Uhudumu wa Wageni (Waiter)", "Mpishi wa Fast Foods"
        ),
        "Uhasibu & Mauzo (Accounting & Sales)" to listOf(
            "QuickBooks & Tally", "Kutunza Vitabu vya Fedha", "Mauzo Dukani (Store Sales)",
            "Kodi na TRA Filing", "Customer Care", "Meneja wa Duka/Bohari"
        ),
        "Saluni & Urembo (Beauty & Salon)" to listOf(
            "Kusuka Nywele na Dreadlocks", "Kupaka Make-Up ya Maharusi", "Kutengeneza Kucha (Nails)",
            "Kinyozi wa Kisasa (Barber)", "Massage na Spa", "Skin Care & Facial"
        ),
        "Kilimo & Mifugo (Agribusiness)" to listOf(
            "Kufuga Kuku wa Kisasa", "Mifumo ya Umwagiliaji (Drip)", "Kupanda Mbogamboga",
            "Ufugaji wa Samaki (Aquaculture)", "Mtaalamu wa Udongo na Mbolea", "Kutunza Bustani"
        )
    )

    fun getInitialJobListings(): List<JobListing> = listOf(
        JobListing(
            title = "Fundi Umeme wa Wiring na Solar (Site Kubwa)",
            employerName = "Kilimanjaro Green Construction Ltd",
            category = "Fundi Umeme (Electrical)",
            requiredSkills = listOf("Wiring ya Nyumba", "Solar Power Installation", "Generators Repair"),
            region = "Dar es Salaam",
            district = "Kariakoo & Mikocheni",
            salaryTsh = 750000,
            salaryPeriod = "Kwa Mwezi",
            jobType = "Kandarasi / Contract",
            description = "Tunahitaji mafundi umeme wawili walioiva kwa ajili ya mradi wa ujenzi wa ghorofa 4. Kazi inajumuisha wiring nzima, kuweka db switch na solar backup system.",
            contactPhone = "+255714889900",
            contactWhatsapp = "+255714889900",
            isUrgent = true,
            isFeatured = true,
            postedDate = "Masaa 2 yaliyopita"
        ),
        JobListing(
            title = "Fundi Bomba wa Haraka (Kuzibua & Matanki)",
            employerName = "White Sands Resort & Apartments",
            category = "Fundi Bomba (Plumbing)",
            requiredSkills = listOf("Mabomba ya Maji", "Water Heater Repair", "Pressure Pumps"),
            region = "Dar es Salaam",
            district = "Mbezi Beach",
            salaryTsh = 60000,
            salaryPeriod = "Kwa Siku",
            jobType = "Kibarua / Gig",
            description = "Kibarua cha siku 3 kufanya marekebisho ya mabomba ya maji moto na pressure pumps kwenye vyumba 6 vya wageni. Malipo ni kila jioni.",
            contactPhone = "+255784112233",
            contactWhatsapp = "+255784112233",
            isUrgent = true,
            isFeatured = false,
            postedDate = "Leo"
        ),
        JobListing(
            title = "Graphic Designer & Social Media Handler",
            employerName = "Serengeti Fresh Agro Brands",
            category = "Teknolojia & Kompyuta (IT & Tech)",
            requiredSkills = listOf("Graphic Design & Canva", "Social Media Marketing", "Website Design & WordPress"),
            region = "Arusha",
            district = "Arusha City",
            salaryTsh = 650000,
            salaryPeriod = "Kwa Mwezi",
            jobType = "Kazi ya Kudumu / Full-time",
            description = "Nafasi ya kijana mbunifu wa picha, mabango ya bidhaa na kuendesha kurasa za Instagram na WhatsApp kwa bidhaa zetu za asili. Uwe na uzoefu wa angalau mwaka 1.",
            contactPhone = "+255768223344",
            contactWhatsapp = "+255768223344",
            isUrgent = false,
            isFeatured = true,
            postedDate = "Jana"
        ),
        JobListing(
            title = "Mafundi Tiles na Gypsum ya Kisasa (Vyumba 8)",
            employerName = "Mhandisi Mwita (Private Homeowner)",
            category = "Ujenzi & Useremala (Construction)",
            requiredSkills = listOf("Kuweka Tiles", "Gypsum & Ceiling", "Kupiga Rangi (Painting)"),
            region = "Dodoma",
            district = "Area D / Mtumba",
            salaryTsh = 1200000,
            salaryPeriod = "Kwa Mradi",
            jobType = "Kandarasi / Contract",
            description = "Nahitaji mafundi makini wa kupiga tiles za 60x60 na kufanya gypsum board design kwenye nyumba yangu mpya Mtumba Dodoma. Vifaa vyote vipo eneo la ujenzi.",
            contactPhone = "+255755998877",
            contactWhatsapp = "+255755998877",
            isUrgent = false,
            isFeatured = false,
            postedDate = "Masaa 5 yaliyopita"
        ),
        JobListing(
            title = "Dereva Binafsi Mwenye Leseni C & Uzoefu",
            employerName = "Afya Bora Logistics",
            category = "Udereva & Usafiri (Driving)",
            requiredSkills = listOf("Dereva wa Gari Binafsi", "Ufundi wa Magari (Mechanic)", "Leseni Daraja C/E"),
            region = "Mwanza",
            district = "Nyamagana",
            salaryTsh = 500000,
            salaryPeriod = "Kwa Mwezi",
            jobType = "Kazi ya Kudumu / Full-time",
            description = "Tunatafuta dereva muaminifu mwenye nidhamu ya hali ya juu kwa ajili ya usafiri wa ofisi na shughuli za usambazaji maduka ya jumla Mwanza mjini.",
            contactPhone = "+255762001122",
            contactWhatsapp = "+255762001122",
            isUrgent = false,
            isFeatured = true,
            postedDate = "Siku 2 zilizopita"
        ),
        JobListing(
            title = "Mshonaji Mahiri wa Vitenge na Suti za Kike",
            employerName = "Zuhura Classic Fashion Studio",
            category = "Ushonaji & Mitindo (Tailoring)",
            requiredSkills = listOf("Kushona Vitenge vya Kike", "Kudarizi (Embroidery)", "Magauni ya Harusi"),
            region = "Dar es Salaam",
            district = "Sinza Palestina",
            salaryTsh = 550000,
            salaryPeriod = "Kwa Mwezi",
            jobType = "Kazi ya Kudumu / Full-time",
            description = "Studio yetu inahitaji fundi cherehani wa kisasa anayeweza kukata mitindo mbalimbali kwa ufasaha. Mashine za kisasa za umeme zinapatikana.",
            contactPhone = "+255717334455",
            contactWhatsapp = "+255717334455",
            isUrgent = true,
            isFeatured = false,
            postedDate = "Leo"
        ),
        JobListing(
            title = "Mhasibu Msaidizi (QuickBooks & TRA EFD)",
            employerName = "Bakhresa Hardware Distributors",
            category = "Uhasibu & Mauzo (Accounting & Sales)",
            requiredSkills = listOf("QuickBooks & Tally", "Kutunza Vitabu vya Fedha", "Kodi na TRA Filing"),
            region = "Zanzibar",
            district = "Stone Town",
            salaryTsh = 800000,
            salaryPeriod = "Kwa Mwezi",
            jobType = "Kazi ya Kudumu / Full-time",
            description = "Nafasi ya mhasibu mdogo mwenye cheti cha Diploma au Degree kuingiza mauzo ya kila siku, kutoa risiti za EFD na kuandaa taarifa za kodi za kila mwezi.",
            contactPhone = "+255777445566",
            contactWhatsapp = "+255777445566",
            isUrgent = false,
            isFeatured = true,
            postedDate = "Jana"
        ),
        JobListing(
            title = "Mtaalamu wa Saluni (Kusuka & Kucha)",
            employerName = "Glory Beauty Parlour",
            category = "Saluni & Urembo (Beauty & Salon)",
            requiredSkills = listOf("Kusuka Nywele na Dreadlocks", "Kutengeneza Kucha (Nails)", "Skin Care & Facial"),
            region = "Mbeya",
            district = "Mbeya City",
            salaryTsh = 400000,
            salaryPeriod = "Kwa Mwezi",
            jobType = "Kazi ya Kudumu / Full-time",
            description = "Tunakaribisha wasusi wenye ujuzi wa mitindo mipya ya rasta, crochet, na urembo wa kucha. Kuna asilimia ya bonasi kwa kila mteja anayehudumiwa.",
            contactPhone = "+255753889911",
            contactWhatsapp = "+255753889911",
            isUrgent = false,
            isFeatured = false,
            postedDate = "Siku 3 zilizopita"
        )
    )
}
