Sonify_Parm : Sonify_AddElt {
	var level;
	// modulateurs (défini dans RecElt)
	// var subs;

	*new {|lvl = -1, mods = #[]|
		^super.new(mods).parmInit(lvl);
	}

	*default {
		^this.new(-1, []);
	}

	parmInit {|lvl|
		level = lvl;
	}

	add {|mod|
		subs.add(mod ? Sonify_Func.default('mod'));
	}

	remove {|mod|
		subs.remove(mod);
	}

	insertTime {|index|
		super.insertTime;
		if (level.isArray)
		{ level = level.insert(index + 1, nil) };
		// this.makeControls;
	}

	removeTime {|index|
		super.removeTime;
		if (level.isArray)
		{ level.removeAt(index + 1) };
		// this.makeControls;
	}

	asString {
		var lvlstr = if (level.isNumber)
		{ level.asString }
		{ "[%]".format(level.collect(_.asString).reduce('+')) };
		^"[%]".format(([lvlstr] ++ subs.collect(_.asString)).reduce('+'));
	}

	// makeControls {
	// 	if (controls.notNil) { controls.destroy };
	// 	controls = HLayoutView(nil);
	// 	if (level.isArray) {
	// 		var last = level.size - 1;
	// 		Knob(controls).mode_(\horiz)
	// 		.value_((level[0]+1)/2)
	// 		.action_ {|v| level[0] = (v.()*2)-1};
	// 		(level.size-2).do {|i|
	// 			var chk = CheckBox(controls).value_(level[i].notNil);
	// 			var knb = Knob(controls).mode_(\horiz)
	// 			.value_(((level[i]?0)+1)/2)
	// 			.action_ {|v| if (chk.()) {level[i] = (v.()*2)-1}};
	// 			chk.action_ {|v| if (v.()) {level[i] = (knb.()*2)-1} { level[i] = nil }};
	// 		};
	// 		Knob(controls).mode_(\horiz)
	// 		.value_((level[last]+1)/2)
	// 		.action_ {|v| level[last] = (v.()*2)-1};
	// 	} {
	// 		Knob(controls).mode_(\horiz)
	// 		.value_((level+1)/2)
	// 		.action_ {|v| level = (v.()*2)-1}
	// 	};
	// 	view.setView(1, controls);
	// }
}