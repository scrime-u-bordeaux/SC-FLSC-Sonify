Sonify_AddElt : Sonify_RecElt {
	*new {|subs|
		^super.new.addEltInit(subs);
	}

	addEltInit {|sbs|
		subs = List.newFrom(sbs);
	}
}
