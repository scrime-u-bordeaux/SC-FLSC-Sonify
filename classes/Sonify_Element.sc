Sonify_Element {
	var <nbTimes;
	var parent;

	// interface graphique
	var view, controls;

	*new {|parent, nbTimes|
		^super.new.elementInit(parent, nbTimes);
	}

	elementInit {|par, num|
		parent = par;
		nbTimes = num ?? {parent.nbTimes};
	}

	insertTime {
		nbTimes = nbTimes + 1;
	}

	removeTime {
		nbTimes = nbTimes - 1;
	}

	makeView {|treeItem|
		view = treeItem;
		this.makeControls;
	}
}