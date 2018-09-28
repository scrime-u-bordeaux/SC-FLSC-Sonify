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

// synthétiser des formules aléatoires
(
// racine de génération aléatoire
var seeds = [6, 13, 42, 27, 51, 421, 667, 1111];
// paramètres
var times, mfuncs, hfuncs, ncol, mods;
// var times = ((2..5) ! 15).reduce('++').asBag;
// var mfuncs = Sonify_Func.orderedArray((Sonify_Func.funcDefs['mass'].keys.asArray ! 3).reduce('++'));
// var hfuncs = (Sonify_Func.funcDefs['harm'].keys.asArray ! 3).reduce('++').asBag;
// var ncol = 30;
// var mods = (Sonify_Func.funcDefs['mod'].keys.asArray ! 10).reduce('++').asBag;
// variables utilisées
var formulas, outFile, server = Sonify_Server();
var job, size, done = true;
var home = Platform.userExtensionDir +/+ "SC-FLSC-Sonify";

{
	seeds.do {|seed|
		"SEED: %".format(seed).postln;
		// initialiser les paramètres
		times = ((2..5) ! 4).reduce('++').asBag;
		mfuncs = Sonify_Func.orderedArray(Sonify_Func.funcDefs['mass'].keys.asArray);
		hfuncs = Sonify_Func.funcDefs['harm'].keys.asBag;
		ncol = 10;
		mods = (Sonify_Func.funcDefs['mod'].keys.asArray ! 3).reduce('++').asBag;
		// initialiser le générateur aléatoire
		thisThread.randSeed = seed;
		// générer les formules
		formulas = Sonify_Formula.randGen(times, mfuncs, hfuncs, ncol, mods);
		size = formulas.size;

		// écrire les formules dans un fichier
		File.mkdir(home +/+ "src");
		outFile = File.open(home +/+ "src" +/+
			"random_%.txt".format(seed), "w");
		formulas.do {|f| outFile.write("%\n".format(f))};
		outFile.close;

		// synthétiser les formules
		// File.mkdir(home +/+ "recordings");
		job = {|i|
			done = false;
			server.job(formulas[i],
				home +/+ "recordings" +/+ "random_%".format(seed) +/+
				"snd%.wav".format((i+1).asString.padLeft(3,"0")),
				{"DONE: %".format(i+1).postln; done = true})
		};

		size.do {|n|
			job.(n);
			while {done.not} {1.wait};
		};

		"FINISHED SEED %".format(seed).postln;
	};

	server.free;
}.fork;
)
