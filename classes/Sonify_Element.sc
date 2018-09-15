Sonify_Element {
	var <>nbTimes;
	var parent;

	// interface graphique
	var view, controls;

	// *new {
	// 	^super.new.elementInit();
	// }

	// elementInit {||
	// }

	setParent {|par|
		parent = par;
		nbTimes = parent.nbTimes;
	}

	insertTime {
		nbTimes = nbTimes + 1;
	}

	removeTime {
		nbTimes = nbTimes - 1;
	}

	// makeView {|treeItem|
	// 	view = treeItem;
	// 	this.makeControls;
	// }
}