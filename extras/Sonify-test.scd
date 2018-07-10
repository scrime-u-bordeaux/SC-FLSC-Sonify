/*
Fichier de test Sonify_Server
*/

(
// initialiser un serveur
var server = Sonify_Server();
// créer une formule
var formula = Sonify_Formula(
	Sonify_Calibre(16),
	Sonify_Time([0.1,1]),
	Sonify_Parm([-1,1,-1]),
	Sonify_Parm(0),
	[
		Sonify_Func(\distquad2, [Sonify_Parm(0,
			[Sonify_Func(\mlfo, [Sonify_Parm(1),Sonify_Parm(0)])])])
	],
	[
		Sonify_Func(\pcyc2, [Sonify_Parm(-1)])
	],
	[
		Sonify_Func(\col, [Sonify_Parm(-1),Sonify_Parm(-0.2),Sonify_Parm(0.2)])
	]
);
// créer une adresse pour le callback
var address = NetAddr("localhost", 7777);
// soumettre une requête
// les paramètres sont:
// - formula: la formule a réaliser (utiliser 'testformula' avec la fréquence en argument)
// - id: l'identifiant de la requête, peut être utile pour l'identifier
//       (si le nom de fichier ne suffit pas)
// - filename: le nom du fichier à produire (avec le chemin)
server.job(formula, Platform.userExtensionDir +/+
	"SC-FLSC-Sonify/recordings/test.WAV",
	{ address.sendMsg("/recordingdone", "test.WAV") }
);
)

// créer une formule et l'afficher
(
Sonify_Formula(
	Sonify_Calibre(16),
	Sonify_Time([0.1,1]),
	Sonify_Parm([-1,1,-1]),
	Sonify_Parm(0),
	[
		Sonify_Func(\distquad2, [Sonify_Parm(0)])
	],
	[],
	[]
).asString;
)

// créer un recepteur OSC
(
o = OSCFunc({|msg, time, addr, recvPort| "Done: %".format(msg.postln)},
	"/recordingdone", recvPort: 7777);
)

// libérer le recepteur OSC
o.free;