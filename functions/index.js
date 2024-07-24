
const functions = require("firebase-functions");
const admin = require("firebase-admin");
const { topic } = require("firebase-functions/v1/pubsub");
admin.initializeApp();

const topicName = "/topics/sync";

exports.onNoteWritten = functions.firestore
.document('Notes/{noteId}').onWrite((snapshot, context) => {
    async (snapshot) => {
        const payload = {
            data: {}
        }

        await admin.messaging().sendToTopic(topicName, payload)
        .then((response) => {
            // Response is a message ID string.
            console.log('Successfully sent message:', response);
          })
          .catch((error) => {
            console.log('Error sending message:', error);
          });
    }
})

// exports.makeUppercase = functions.firestore.document("/messages/{documentId}")
//     .onCreate((snap, context) => {
//       const original = snap.data().original;
//       console.log("Uppercasing", context.params.documentId, original);
//       const uppercase = original.toUpperCase();
//       return snap.ref.set({uppercase}, {merge: true});
//     });
