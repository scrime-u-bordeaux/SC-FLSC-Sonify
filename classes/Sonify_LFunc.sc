Sonify_LFunc : Sonify_AddElt {
	// type de la liste
	var type;
	// liste de fonctions (défini dans RecElt)
	// var subs;

	*new {|type, funcList = #[]|
		^super.new(funcList)/*.lFuncInit()*/;
	}

	// lFuncInit {|fList|
	// }

	*randGen {|time, type, flist, mods|
		var size = flist.size;
		var mdspl = {List()} ! size;
		mods.do {|mod| mdspl[rand(size)].add(mod)};
		^this.new(type,
			flist.collectAs({|func, i|
				Sonify_Func.randGen(time, type, func, mdspl[i])
			}, Array)
		);
	}

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