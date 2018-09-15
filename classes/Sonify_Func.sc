Sonify_Func : Sonify_RecElt {
	classvar <funcDefs, massOrder;

	var selector, type, <order;
	// paramètres (défini dans RecElt)
	// var subs;

	*initClass {
		funcDefs = Dictionary.newFrom([
			// fonctions de masse
			mass: Dictionary.newFrom([
				none: 0,
				red: 1,
				brt: 1,
				surd1: 1,
				surd2: 1,
				surd3: 1,
				cald: 1,
				distquad: 2,
				distquad2: 1,
				distexp: 1,
				decl: 1
			]),
			// fonctions de timbre
			harm: Dictionary.newFrom([
				none: 0,
				bharm: 1,
				comb: 1,
				degr: 1,
				dom: 2,
				pcyc2: 1,
				pcyc3: 2,
				pcyc4: 3,
				plog1: 1,
				plog3: 2
			]),
			// fonction de couleur
			col: Dictionary.newFrom([
				col: 3
			]),
			// modulateurs
			mod: Dictionary.newFrom([
				none: 0,
				mlfo: 2,
				mtri: 2,
				msqu: 2
			])
		]);

		massOrder = Dictionary.newFrom([
			red: 1,
			brt: 2,
			surd1: 3,
			surd2: 3,
			surd3: 3,
			cald: 4,
			distquad: 5,
			distquad2: 5,
			distexp: 6,
			decl: 7
		]);
	}

	*new {|type, selector, parms = #[]|
		^super.new.funcInit(type, selector, parms);
	}

	*default {|type|
		^this.new(type, 'none', []);
	}

	funcInit {|tp, sel, prms|
		var defs, nbParms;

		// vérifier le type
		defs = funcDefs[tp];
		if (defs.isNil)
		{ Error("Type % does not exist.".format(tp)).throw };

		// le type 'col' n'a qu'un seul membre
		if (tp == 'col') {sel = 'col'};

		nbParms = defs[sel];
		// vérifier que la fonction existe
		if (nbParms.isNil)
		{ Error("Function % does not exist.".format(sel)).throw };
		// vérifier que le nombre de paramètres est correct
		if (nbParms > prms.size)
		{
			// remplir avec des paramètres par défaut
			prms = prms ++ ({Sonify_Parm.default} ! (nbParms - prms.size));
			// Error("Function %: wrong number of parameters".format(sel)).throw;
		};
		if (nbParms < prms.size)
		{
			// remplir avec des paramètres par défaut
			prms = prms[..nbParms - 1];
			// Error("Function %: wrong number of parameters".format(sel)).throw;
		};
		type = tp;
		selector = sel;
		subs = prms;
		order = massOrder[selector];
	}

	asString {
		if (selector == \none)
		{ Error("Uninitialized function.").throw };
		^"[% %]".format(selector.asString, subs.collect(_.asString).reduce('+'));
	}
}
