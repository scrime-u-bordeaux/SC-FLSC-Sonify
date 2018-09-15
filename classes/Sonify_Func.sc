Sonify_Func : Sonify_Element {
	classvar funcParms, massOrder;

	var selector, parms, <order;

	*initClass {
		funcParms = Dictionary.newFrom([
			// fonction par défaut
			none: 0,
			// fonctions de masse
			red: 1,
			brt: 1,
			surd1: 1,
			surd2: 1,
			surd3: 1,
			cald: 1,
			distquad: 2,
			distquad2: 1,
			distexp: 1,
			decl: 1,
			// fonctions de timbre
			bharm: 1,
			comb: 1,
			degr: 1,
			dom: 2,
			pcyc2: 1,
			pcyc3: 2,
			pcyc4: 3,
			plog1: 1,
			plog3: 2,
			// fonction de couleur
			col: 3,
			// modulateurs
			mlfo: 2,
			mtri: 2,
			msqu: 2
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

	*new {|sel, parms = #[], parent|
		^super.new(parent).funcInit(sel, parms);
	}

	*default {|parent|
		^this.new(\none, [], parent);
	}

	funcInit {|sel, prms|
		// vérifier que la fonction existe
		if (funcParms[sel].isNil)
		{ Error("Function % does not exist.".format(sel)).throw };
		// vérifier que le nombre de paramètres est correct
		if (funcParms[sel] != prms.size)
		{ Error("Function %: wrong number of parameters".format(sel)).throw };
		selector = sel;
		parms = prms;
		order = massOrder[sel];
	}

	insertTime {|index|
		super.insertTime;
		parms.do(_.insertTime(index));
	}

	removeTime {|index|
		super.removeTime;
		parms.do(_.removeTime(index));
	}

	asString {
		if (selector == \none)
		{ Error("Uninitialized function.").throw };
		^"[% %]".format(selector.asString, parms.collect(_.asString).reduce('+'));
	}
}