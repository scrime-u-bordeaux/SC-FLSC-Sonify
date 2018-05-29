/*
Fichier de test Sonify_Server
*/

// initialiser un serveur
~server = Sonify_Server();

// créer les paramètres
(
~testformula = {|freq|
	"(base  [0 2]" +
	"  (m* (sign [0.1 (- dur 0.1)]" +
	"        (seq [(mseg 0 0.2) (mc 0.2) (mseg 0.2 0)]))" +
	"      (mosc % 0)))".format(freq);
};
~id = "TestID";
~file = {|freq|
	Platform.userExtensionDir +/+ "SC-FLSC-Sonify" +/+ "recordings" +/+
	"testrec%.WAV".format(freq);
}
)

// définir la fréquence
~freq = 667;

// soumettre une requête
~server.job(~testformula.(~freq), ~id, ~file.(~freq));

// libérer le serveur
~server.free;
