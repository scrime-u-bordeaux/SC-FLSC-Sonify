/*
Fichier de test Sonify_Server
*/

// initialiser un serveur
~server = Sonify_Server();

// créer les fonctions utilitaires nécessaires
// il s'agit de variables globales (donc précédées de '~')
(
// formule de test
// sinusoïde de la fréquence souhaitée avec enveloppe trapézoïdale
~testformula = {|freq|
	// définir un support temporel
	"(base  [0 2]" +
	// effectuer le produit de l'enveloppe dynamique et de l'oscillateur
	// appliquer la signature sur l'enveloppe
	"  (m* (sign [0.1 (- dur 0.1)]" +
	// composition séquentielle de trois segments suivant la signature
	"        (seq [(mseg 0 0.2) (mc 0.2) (mseg 0.2 0)]))" +
	// oscillateur à la fréquence requise
	"      (mosc % 0)))".format(freq);
};
// identifiant de la requête (pourra être supprimé si inutile)
~id = "TestID";
// nom de fichier: fonction qui dépend des paramètres (fréquence)
~file = {|freq|
	Platform.userExtensionDir +/+ "SC-FLSC-Sonify" +/+ "recordings" +/+
	"testrec%.WAV".format(freq);
}
)

// définir la fréquence
~freq = 667;

// soumettre une requête
// les paramètres sont:
// - formula: la formule a réaliser (utiliser 'testformula' avec la fréquence en argument)
// - id: l'identifiant de la requête, peut être utile pour l'identifier
//       (si le nom de fichier ne suffit pas)
// - filename: le nom du fichier à produire (avec le chemin)
~server.job(~testformula.(~freq), ~id, ~file.(~freq));

// libérer le serveur
~server.free;
