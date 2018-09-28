Sonify_Formula : Sonify_Element {
	// variables d'instance
	var cal, time, dyn, mel, lmass, lharm, lcol;

	*new {|cal, time, dyn, mel, lmass, lharm, lcol|
		^super.new.formulaInit(cal, time, dyn, mel, lmass, lharm, lcol);
	}

	formulaInit {|pcal, ptime, pdyn, pmel, plmass, plharm, plcol|
		time = ptime.setParent(this);
		cal = pcal.setParent(this);
		dyn = pdyn.setParent(this);
		mel = pmel.setParent(this);
		lmass = plmass.setParent(this);
		lharm = plharm.setParent(this);
		lcol = plcol.setParent(this);
		^this;
	}

	*default {
		^super.new(2).formulaDefaultInit;
	}

	formulaDefaultInit {
		^this.formulaInit(
			Sonify_Calibre.default,
			Sonify_Time.default,
			Sonify_Dyn.default,
			Sonify_Parm.default,
			Sonify_LFunc(\mass),
			Sonify_LFunc(\harm),
			Sonify_LFunc(\col)
		);
	}

	insertTime {|index|
		super.insertTime;
		[cal, dyn, mel, lmass, lharm, lcol].do(_.insertTime(index));
	}

	removeTime {|index|
		super.insertTime;
		[cal, dyn, mel, lmass, lharm, lcol].do(_.removeTime(index));
	}

	// génération aléatoire de formules
	// times: Bag de longueurs (segments)
	// le nombre de formules est la taille de ce Bag
	// mfuncs: tableau de Bags de fonctions de masse (indexées par ordre)
	// hfuncs: Bag de fonctions de timbre
	// ncol: nombre de fonctions de couleur
	// mods: Bag de modulateurs
	*randGen {|times, mfuncs, hfuncs, ncol, mods|
		if (times.size > 1)
		{
			// cas récursif
			var left, right;
			var numl, numr, n;
			var tl, mfl, hfl, ncl, ml;
			// partager les éléments
			// temps
			// numl = rand(times.size-1) + 1;
			numl = (times.size / 2).floor.asInteger
			+ ((times.size % 2) * 0.5.coin.asInteger);
			numr = times.size - numl;
			tl = Bag(numl);
			numl.do {
				var tmp = times.choose;
				tl.add(tmp);
				times.remove(tmp);
			};
			// fonctions de masse
			mfl = mfuncs.collect {|ord|
				var res;
				var nmax = min(numl, ord.size);
				var nmin = max(ord.size-numr, 0);
				// n = rand(nmax-nmin+1) + nmin;
				n = ((nmax-nmin) / 2).floor.asInteger
				+ (((nmax-nmin) % 2) * 0.5.coin.asInteger)
				+ nmin;
				res = Bag(n);
				n.do {
					var tmp = ord.choose;
					res.add(tmp);
					ord.remove(tmp);
				};
				res;
			};
			// fonctions de timbre
			// n = rand(hfuncs.size+1);
			n = (hfuncs.size / 2).floor.asInteger
			+ ((hfuncs.size % 2) * 0.5.coin.asInteger);
			hfl = Bag(n);
			n.do {
				var tmp = hfuncs.choose;
				hfl.add(tmp);
				hfuncs.remove(tmp);
			};
			// nombre de couleurs
			// ncl = rand(ncol+1);
			ncl = (ncol / 2).floor.asInteger
			+ ((ncol % 2) * 0.5.coin.asInteger);
			ncol = ncol - ncl;
			// modulateurs
			n = rand(mods.size+1);
			n = (mods.size / 2).floor.asInteger
			+ ((mods.size % 2) * 0.5.coin.asInteger);
			ml = Bag(n);
			n.do {
				var tmp = mods.choose;
				ml.add(tmp);
				mods.remove(tmp);
			};

			// rappeler sur chaque partie
			left = this.randGen(tl, mfl, hfl, ncl, ml);
			right = this.randGen(times, mfuncs, hfuncs, ncol, mods);

			// retourner la concatenation des résultats
			^left++right;
		} {
			// cas de base
			var time = times.choose;
			var massfuncs = mfuncs.reduce('++');
			// partager les modulateurs
			var probs = [1, 1, massfuncs.size, hfuncs.size, ncol].normalizeSum;
			var modspl = {List()} ! 5;
			mods.do {|mod| modspl[probs.windex].add(mod)};
			^[Sonify_Formula(
				Sonify_Calibre.randGen,
				Sonify_Time.randGen(time),
				Sonify_Dyn.randGen(time, modspl[0]),
				Sonify_Parm.randGen(time, modspl[1]),
				Sonify_LFunc.randGen(time, 'mass', massfuncs, modspl[2]),
				Sonify_LFunc.randGen(time, 'harm', hfuncs, modspl[3]),
				Sonify_LFunc.randGen(time, 'col',  'col'!ncol, modspl[4])
			)];
		}
	}

	// makeView {|window|
	// 	view = TreeView(window);
	// 	view.columns = ["",""];
	// 	cal.makeView(view.addChild(["calibre",""]));
	// 	time.makeView(view.addChild(["temps",""]));
	// 	dyn.makeView(view.addChild(["amplitude",""]));
	// 	mel.makeView(view.addChild(["frequence",""]));
	// }

	// retourne la chaîne FLSC associée
	asString {
		^"(sonify % % % % % % %)".format(cal, time, dyn, mel, lmass, lharm, lcol);
	}
}
