const startButtons = document.querySelectorAll('.startButton');
const stopButtons = document.querySelectorAll('.stopButton');

let recognitions = {};
let transcriptsArrays = {};

startButtons.forEach((startButton, index) => {
    const stopButton = stopButtons[index];
    const transcriptionSpan = document.querySelector(`#transcription${index + 1}`);

    recognitions[index] = new (window.SpeechRecognition || window.webkitSpeechRecognition)();
    transcriptsArrays[index] = [];

    startButton.addEventListener('click', () => startRecording(index));
    stopButton.addEventListener('click', () => stopRecording(index));

    function startRecording(index) {
        recognitions[index].lang = 'ja-JP';
        recognitions[index].interimResults = true;

        recognitions[index].onresult = function (event) {
            const result = event.results[event.results.length - 1];
            const transcript = result[0].transcript;

            if (result.isFinal) {
                transcriptsArrays[index].push(transcript);
                transcriptionSpan.innerHTML = transcript;
                console.log(transcript);
            }
        };

        recognitions[index].onend = function () {
            startButton.disabled = false;
            stopButton.disabled = true;

            sendTranscriptsToServer(transcriptsArrays[index]);
            transcriptsArrays[index] = [];
        };

        startButton.disabled = true;
        stopButton.disabled = false;

        recognitions[index].start();
    }

    function stopRecording(index) {
        recognitions[index].stop();
        startButton.disabled = false;
        stopButton.disabled = true;
    }
});

function sendTranscriptsToServer(transcriptsArray) {
    console.log('Transcripts to send to server:', transcriptsArray);
}