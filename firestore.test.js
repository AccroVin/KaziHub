const {
  initializeTestEnvironment,
  assertFails,
  assertSucceeds,
} = require("@firebase/rules-unit-testing");
const { test, before, after, beforeEach } = require("node:test");
const fs = require("node:fs");

let testEnv;
const PROJECT_ID = process.env.GCP_PROJECT || "demo-no-project";
const ALICE_UID = "alice_123";
const BOB_UID = "bob_456";

const [emulatorHost, emulatorPortStr] = (process.env.FIRESTORE_EMULATOR_HOST || "127.0.0.1:8085").split(":");
const emulatorPort = parseInt(emulatorPortStr, 10);

before(async () => {
  const rules = fs.readFileSync("./firestore.rules", "utf8");
  testEnv = await initializeTestEnvironment({
    projectId: PROJECT_ID,
    firestore: {
      rules,
      host: emulatorHost,
      port: emulatorPort,
    },
  });
});

after(async () => {
  if (testEnv) {
    await testEnv.cleanup();
  }
});

beforeEach(async () => {
  if (testEnv) {
    await testEnv.clearFirestore();
  }
});

// --- User Profile Tests ---

test("Unauthenticated user: cannot write profile", async () => {
  const unauthDb = testEnv.unauthenticatedContext().firestore();
  await assertFails(
    unauthDb.collection("users").doc(ALICE_UID).set({
      userId: ALICE_UID,
      fullName: "Alice Mwita",
      phoneNumber: "+255714001122",
      primaryTrade: "Fundi Umeme",
      region: "Dar es Salaam",
      createdAt: new Date(),
    })
  );
});

test("Authenticated user: can create their own profile", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).set({
      userId: ALICE_UID,
      fullName: "Alice Mwita",
      phoneNumber: "+255714001122",
      primaryTrade: "Fundi Umeme",
      region: "Dar es Salaam",
      createdAt: new Date(),
    })
  );
});

test("Authenticated user: cannot create another user's profile", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  await assertFails(
    aliceDb.collection("users").doc(BOB_UID).set({
      userId: BOB_UID,
      fullName: "Bob Juma",
      phoneNumber: "+255714001122",
      primaryTrade: "Fundi Umeme",
      region: "Dar es Salaam",
      createdAt: new Date(),
    })
  );
});

// --- Job Listings Tests ---

test("Unauthenticated user: cannot create job vacancy", async () => {
  const unauthDb = testEnv.unauthenticatedContext().firestore();
  await assertFails(
    unauthDb.collection("jobs").doc("job_1").set({
      userId: ALICE_UID,
      title: "Solar Installation",
      employerName: "Kilimanjaro Ltd",
      category: "Fundi Umeme",
      region: "Dar es Salaam",
      salaryTsh: 600000,
      contactPhone: "+255714001122",
      createdAt: new Date(),
    })
  );
});

test("Authenticated user: can create job listing", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  await assertSucceeds(
    aliceDb.collection("jobs").doc("job_1").set({
      userId: ALICE_UID,
      title: "Solar Installation",
      employerName: "Kilimanjaro Ltd",
      category: "Fundi Umeme",
      region: "Dar es Salaam",
      salaryTsh: 600000,
      contactPhone: "+255714001122",
      createdAt: new Date(),
    })
  );
});

test("Authenticated user: can read job listings", async () => {
  await testEnv.withSecurityRulesDisabled(async (context) => {
    await context.firestore().collection("jobs").doc("job_1").set({
      userId: ALICE_UID,
      title: "Solar Installation",
      employerName: "Kilimanjaro Ltd",
      category: "Fundi Umeme",
      region: "Dar es Salaam",
      salaryTsh: 600000,
      contactPhone: "+255714001122",
      createdAt: new Date(),
    });
  });

  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  await assertSucceeds(bobDb.collection("jobs").doc("job_1").get());
});

test("Bob cannot delete Alice's job listing", async () => {
  await testEnv.withSecurityRulesDisabled(async (context) => {
    await context.firestore().collection("jobs").doc("job_1").set({
      userId: ALICE_UID,
      title: "Solar Installation",
      employerName: "Kilimanjaro Ltd",
      category: "Fundi Umeme",
      region: "Dar es Salaam",
      salaryTsh: 600000,
      contactPhone: "+255714001122",
      createdAt: new Date(),
    });
  });

  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  await assertFails(bobDb.collection("jobs").doc("job_1").delete());
});

// --- Applications Tests ---

test("Authenticated user: can submit application for themselves", async () => {
  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  await assertSucceeds(
    bobDb.collection("applications").doc("app_1").set({
      applicantId: BOB_UID,
      jobId: "job_1",
      jobTitle: "Solar Installation",
      employerName: "Kilimanjaro Ltd",
      createdAt: new Date(),
    })
  );
});

test("Alice cannot read Bob's applications", async () => {
  await testEnv.withSecurityRulesDisabled(async (context) => {
    await context.firestore().collection("applications").doc("app_1").set({
      applicantId: BOB_UID,
      jobId: "job_1",
      jobTitle: "Solar Installation",
      employerName: "Kilimanjaro Ltd",
      createdAt: new Date(),
    });
  });

  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  await assertFails(aliceDb.collection("applications").doc("app_1").get());
});
