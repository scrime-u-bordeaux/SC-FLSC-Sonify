Sonify_Server {
	// le FLSC_Interpreter
	var interpreter;

	*new {
		// instanciation de base
		// appelle le constructeur de la superclasse, jusqu'à Object
		// puis effectue l'initialisation
		^super.new.serverInit();
	}

	serverInit {
		// initialiser l'interpréteur
		// remplacer par:
		// interpreter = FLSC_Interpreter().loadPackage("chemin_du_paquet_Sonify");
		interpreter = FLSC_Interpreter();
	}

	job {|formula, id, filename|
		// rendu effectué de façon asynchrone: Function.fork
		{
			// évaluer la formule avec le callback souhaité
			interpreter.read(formula).recordNRT(filename,
				// renvoyer un message précisant l'identifiant et le nom de fichier
				// le nom de fichier pourrait suffire
				// ou bien il pourrait être généré automatiquement
				doneAction: {"%: %".format(id, filename).postln};
			)
		}.fork;
		// se retourne lui-même (implicitement):
		// ^this;
		// mais peut retourner toute autre valeur utile
	}

	// lorsqu'on a terminé
	free {
		// libérer l'interpréteur
		interpreter.free;
		// libérer l'Object lui-même
		^super.free;
	}
}