/*
Fichier de test Sonify_Server
*/

(
// initialiser un serveur
var server = Sonify_Server();
// créer une formule
var formula = Sonify_Formula(16, [0.1, 1.9, 1.9, 0.1], [[-1, 0, nil, 0, -1]],
	[0, ["mlfo", [[-0.9, nil, -1, nil, -1]],[0]]],
	[["brt", [[-1, nil, nil, nil, 1]]]],
	/*[["degr", [-1]]]*/[], []);
// soumettre une requête
// les paramètres sont:
// - formula: la formule a réaliser (utiliser 'testformula' avec la fréquence en argument)
// - id: l'identifiant de la requête, peut être utile pour l'identifier
//       (si le nom de fichier ne suffit pas)
// - filename: le nom du fichier à produire (avec le chemin)
server.job(formula, "TestID", Platform.userExtensionDir +/+
	"SC-FLSC-Sonify/recordings/test.WAV");
)
