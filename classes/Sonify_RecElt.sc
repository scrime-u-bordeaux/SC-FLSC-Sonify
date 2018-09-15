Sonify_RecElt : Sonify_Element {
	var subs;

	setParent {|par|
		super.setParent(par);
		subs.do(_.setParent(par));
	}

	insertTime {|index|
		super.insertTime;
		subs.do(_.insertTime(index));
	}

	removeTime {|index|
		super.insertTime;
		subs.do(_.removeTime(index));
	}
}

