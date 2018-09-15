Sonify_LFunc : Sonify_AddElt {
	// types de fonction
	// classvar funcTypes;

	// type de la liste
	var type;
	// liste de fonctions (défini dans RecElt)
	// var subs;

	// *initClass {
	// 	Class.initClassTree(Sonify_Func);
	// 	funcTypes = Dictionary(3);
	// 	['mass', 'harm', 'col'].do {|type|
	// 		funcTypes.add(type ->
	// 			Sonify_Func.funcDefs.select {|def| def.first == type}.keys
	// 			.asArray.sort {|a,b| a.asString < b.asString}
	// 		)
	// 	};
	// }

	*new {|type, funcList = #[]|
		^super.new(funcList)/*.lFuncInit()*/;
	}

	// lFuncInit {|fList|
	// }

	add {|func|
		subs.add(func ? Sonify_Func.default(type));
	}

	remove {|func|
		subs.remove(func);
	}

	asString {
		if (type == 'mass') {
			// trier les fonctions de masse
			subs.sort {|a,b| a.order < b.order};
			// vérifier l'absence de doublons
			subs.doAdjacentPairs {|a,b|
				if (a.order == b.order)
				{ Error("Duplicate mass functions.").throw };
			};
		};
		^"[%]".format(subs.collect(_.asString).reduce('+') ? "");
	}
}