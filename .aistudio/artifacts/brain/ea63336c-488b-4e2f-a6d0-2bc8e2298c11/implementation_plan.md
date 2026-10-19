# KaziHub Tanzania — Skills & Job Matching Platform

KaziHub Tanzania is a dedicated platform designed to tackle unemployment across Tanzania by empowering artisans, technicians, and professionals to register their mastered skills, build a verified work profile, and instantly connect with high-paying job opportunities and employers across all Tanzanian regions.

---

## User Review & Critical Decisions

> [!IMPORTANT]
> The following architectural and product decisions were confirmed during the interactive clarification interview:

- **Brand & Identity**: **KaziHub Tanzania** with a distinctive Tanzanian Emerald Green, Sun Gold, and Deep Obsidian palette representing national craftsmanship, growth, and trust.
- **Premium Tier**: **TSh 3,000/week** and **TSh 10,000/month** giving job seekers a **Verified Pro Badge**, **Priority Job Alerts**, **Featured Top Placement** in employer searches, and **Direct WhatsApp / Call contact** access with employers.
- **Skills Coverage**: Comprehensive hybrid catalog spanning practical trades (*Fundi Umeme, Fundi Bomba, Ujenzi, Udereva, Ushonaji, Wapishi, Kilimo*) and professional/digital skills (*Software, Graphic Design, Uhasibu/Accounting, Digital Marketing, Sales*).
- **Bilingual & Localized**: One-tap toggle between **Kiswahili** and **English** with localized city filters (*Dar es Salaam, Arusha, Dodoma, Mwanza, Mbeya, Zanzibar, Morogoro, Tanga*).

---

## 1. Overview & Core Concept

### What It Does
- **Worker Skill Registration**: Tanzanians can create an attractive, verified profile showcasing their trade/profession, years of experience, past project photos/portfolio, certification status, hourly/daily/monthly rates in TSh, and contact channels (WhatsApp & Phone).
- **Skill-Based Job Discovery**: Smart matching engine that filters vacancies by skill keywords, experience level, city/district, salary range (TSh), and job type (Full-time, Part-time, Gig/Kibarua, Contract).
- **Direct Employer Connection**: Job seekers can apply directly via phone call, in-app application submission, or direct WhatsApp chat with hiring managers.
- **Employer Job Posting**: Employers, businesses, and homeowners can post vacancies in under 2 minutes specifying required skills, budget, location, and contact details.
- **Premium Pro Subscriptions (TSh)**: Seamless subscription management supporting standard Tanzanian Mobile Money payment flows (Vodacom M-Pesa, Tigo Pesa, Airtel Money, HaloPesa) for weekly (TSh 3,000) or monthly (TSh 10,000) plans.

### Target Audience
1. **Skilled Tanzanian Youth & Artisans ("Mafundi")**: Electricians, plumbers, builders, carpenters, mechanics, chefs, tailors, domestic staff seeking regular clients and gigs.
2. **Digital & Office Professionals**: Accountants, marketers, developers, translators, and graduates looking for formal contracts and freelance gigs.
3. **Employers & Homeowners**: Businesses, construction managers, families, and startup founders seeking reliable, vetted skills without agency middlemen.

---

## 2. User Experience & Visual Design

### Key User Flows

1. **Onboarding & Skill Profile Creation**:
   - Welcome screen highlighting "Unganishwa na Kazi za Ujuzi Wako" (Get Connected to Jobs Matching Your Skills).
   - Skill registration wizard: Select skill category -> specify mastered sub-skills -> set experience years -> set expected daily/monthly rate (TSh) -> add bio & WhatsApp phone number.
2. **Job Discovery & Matching Feed**:
   - Dynamic search with category pills (*Fundi Umeme, Ujenzi, IT & Tech, Uhasibu, Udereva, etc.*).
   - "Matching Your Skills" highlighted tab displaying jobs where the user's registered skills match employer requirements.
   - Filter drawer by Region (*Kinondoni, Ilala, Ubungo, Arusha City, Dodoma CBD, etc.*) and salary range.
3. **Job Details & Fast Direct Application**:
   - Rich job view: company/employer name, verified tag, compensation (TSh), job duties, requirements, and deadline.
   - Fast action buttons: "Tuma Maombi" (Apply in-app) and "Wasiliana kwa WhatsApp" (Chat on WhatsApp).
4. **Post-a-Job (Employer Mode)**:
   - Quick toggle to post vacancies, set required skills, job description, payment offer in TSh, and location.
5. **KaziHub Pro (Subscription & Benefits)**:
   - Tier selection: Weekly (TSh 3,000) vs Monthly (TSh 10,000 - Save 25%).
   - Feature comparison: Verified checkmark badge, 3x profile views, immediate SMS/WhatsApp priority alerts, top search ranking.
   - Mobile Money checkout modal (Vodacom M-Pesa, Tigo Pesa, Airtel Money) with simulated USSD prompt & instant receipt activation.

### Visual Identity & Theme
- **Primary Color**: Emerald Green (`#00A859`) — symbolizes prosperity, productivity, and growth in Tanzania.
- **Secondary / Accent**: Sun Gold (`#FFB800` / `#FFC107`) — highlights premium features, verified badges, and high-paying jobs.
- **Tertiary Accent**: Ocean Blue (`#0288D1`) — represents professional communication and trust.
- **Surface & Backgrounds**: Crisp clean surfaces with subtle emerald tint, dark mode obsidian surfaces (`#121820` / `#1E2631`).
- **Typography**: Clean modern type scale with bold headers for Swahili and English readability.

---

## 3. Key Product Decisions & Trade-Offs

- **Local-First Room Database with Pre-Seeded Real Tanzanian Market Data**:
  - *Chosen Approach*: Android Room SQLite database pre-populated with realistic, authentic job vacancies across Dar es Salaam, Dodoma, Arusha, Mwanza, and worker profiles, while supporting full offline CRUD for user registrations and saved jobs.
  - *Why*: Instant zero-latency loading, works in low-connectivity environments typical across Tanzania, and requires zero external cloud signup friction for testing and production readiness.
- **Simulated Tanzanian Mobile Money Flow (M-Pesa / Tigo Pesa)**:
  - *Chosen Approach*: Realistic Tanzanian carrier selection (Vodacom, Tigo, Airtel, Halotel), phone number prompt with validation (`+255...`), simulated USSD push confirmation, and instant receipt generation with active badge storage in Room.
  - *Why*: Provides the exact visual and operational UX expected by Tanzanian users without requiring live sandbox merchant KYC credentials.
- **Bilingual English/Swahili In-App Localization Engine**:
  - *Chosen Approach*: Dynamic in-memory and state-persisted language toggle with Swahili and English dictionaries covering all UI strings and category labels.
  - *Why*: Swahili is the lingua franca in Tanzania; providing both Swahili and English ensures universal accessibility for both local artisans and international employers.

---

## 4. Technical Architecture & Data Strategy

```
┌────────────────────────────────────────────────────────────────────────┐
│                          KaziHub Tanzania UI                           │
│  ┌──────────────────┐  ┌──────────────────┐  ┌──────────────────────┐  │
│  │   JobFeedScreen  │  │  JobDetailScreen │  │  WorkerProfileScreen │  │
│  └────────┬─────────┘  └────────┬─────────┘  └──────────┬───────────┘  │
│  ┌────────┴─────────┐  ┌────────┴─────────┐  ┌──────────┴───────────┐  │
│  │  PostJobScreen   │  │  KaziHubProModal │  │ Language/City Filter │  │
│  └──────────────────┘  └──────────────────┘  └──────────────────────┘  │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │ Jetpack Compose StateFlow
┌───────────────────────────────────▼────────────────────────────────────┐
│                  KaziHubViewModel & AppState                           │
│  - Current User Profile State (Registered Skills, Rate, Bio, Location) │
│  - Active Job Listings & Filter Predicates                             │
│  - Language State (SW / EN)                                            │
│  - Subscription State (Free vs Pro Active, Expiry Date)                │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │
┌───────────────────────────────────▼────────────────────────────────────┐
│                         KaziHubRepository                              │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │ Room Database (KaziHubDatabase):                                 │  │
│  │ - WorkerProfileDao: Save & edit worker bio & skills              │  │
│  │ - JobListingDao: Insert, query, filter vacancies                 │  │
│  │ - ApplicationDao: Track job applications & timestamps            │  │
│  │ - SubscriptionDao: Save active premium tier and receipts         │  │
│  └──────────────────────────────────────────────────────────────────┘  │
└────────────────────────────────────────────────────────────────────────┘
```

### Data Entities & Schema
- `WorkerProfile`: `id`, `name`, `phoneNumber`, `email`, `region`, `district`, `primaryTrade`, `skillsList` (JSON/Comma), `yearsExperience`, `hourlyRateTsh`, `bio`, `isVerifiedPro`, `profileImageUri`.
- `JobListing`: `id`, `title`, `companyName`, `category`, `requiredSkills`, `region`, `locationDetail`, `salaryMinTsh`, `salaryMaxTsh`, `salaryPeriod` (Per Day / Per Month / Project), `jobType`, `description`, `contactPhone`, `contactWhatsapp`, `postedDate`, `isFeatured`.
- `JobApplication`: `id`, `jobId`, `workerId`, `appliedAt`, `status`, `coverNote`.
- `Subscription`: `id`, `tier` (Weekly vs Monthly), `amountTsh`, `carrier` (M-Pesa / Tigo Pesa / Airtel), `phoneNumber`, `activatedAt`, `expiresAt`, `isActive`.

---

## 5. Implementation Roadmap & Verification

1. **Resources & Metadata**:
   - Update `metadata.json` with `name: "KaziHub Tanzania"` and description.
   - Update `res/values/strings.xml` with `app_name = "KaziHub Tanzania"`.
   - Generate custom adaptive app icon & foreground vector matching the KaziHub brand.
2. **Database & Data Layer**:
   - Implement `Room` entities, DAOs, and pre-populated Tanzanian job listings (e.g., Construction Site Electrician in Kariakoo, Solar Technician in Arusha, Mobile App Developer in Sinza, Head Chef in Masaki, Accountant in Dodoma).
3. **Repository & ViewModel**:
   - Reactive repository managing profile registration, job filtering, applications, and mobile money premium upgrades.
4. **Jetpack Compose UI Screens**:
   - Top App Bar with Bilingual Swahili/English toggle and Pro status badge.
   - Bottom Navigation: *Kazi (Jobs)*, *Wasifu Wangu (My Profile)*, *Weka Tangazo (Post Job)*, *KaziHub Pro (Premium)*.
   - Comprehensive filter bar by skill category, region, and salary.
   - Detail view with direct WhatsApp & phone call intent launchers.
   - Interactive Mobile Money payment modal with USSD simulation and receipt.
5. **Compilation & Verification**:
   - Execute `compile_applet` to verify clean build and flawless execution.
