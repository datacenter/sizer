angular.module('webappApp').constant("SERVICE_STATIC_JSON", {
	getAppsUri : "static_jsons/apps.json"
})

angular.module('webappApp').constant("REST_URI", {
	projectUri : "acisizer/v1/project/",
	getProjectUri : "acisizer/v1/projects",
	aciSizerUri: "acisizer/v1"
})

angular.module('webappApp').constant("ERROR_STRING_CONSTANTS", {
	FORBIDDEN_APPLICATION_MESSAGE : "Cannot create application under tenant : Common",
	FORBIDDEN_APPLICATION_TITLE : "Forbidden"
})

angular.module('webappApp').constant("DIAGRAM_CONSTANTS", {
	app : {
		options : {
			editable : {tools : ["edit"]},
			width : 55,
			height : 55,
			type : "app",
			path : "M128 5C60.2 5 5 60.2 5 128s55.2 123 123 123s123-55.2 123-123S195.8 5 128 5z M128 245 c-64.6 0-117-52.4-117-117S63.4 11 128 11s117 52.4 117 117S192.6 245 128 245z",
			path2 :"M92.3 42L77.8 82h6.7l3.8-11h14.9l3.9 11h6.8L99.2 42H92.3z M90.1 65l3.5-10c0.9-2.6 1.6-4.9 2.1-7.1c0.2 0.7 0.5 1.9 1 3.4c0.5 1.5 0.8 2.5 1.1 3.3l3.6 10.5H90.1zM141.1 45.8c-2.4-2-6.1-2.8-10.9-2.8H119v39h6V67h4.3c4.9 0 8.7-1.1 11.4-3.3c2.7-2.2 4-5.2 4-9.3			C144.7 50.6 143.5 47.8 141.1 45.8zM135.9 60.1c-1.5 1.1-3.9 1.9-7.2 1.9H125V48h4.7c2.9 0 5 0.5 6.4 1.6c1.4 1.1 2 2.8 2 5.1			C138.2 57.1 137.4 59 135.9 60.1z M174.6 45.8c-2.4-2-6.1-2.8-10.9-2.8H152v39h7V67h3.9c4.9 0 8.7-1.1 11.4-3.3c2.7-2.2 4-5.2 4-9.3			C178.2 50.6 177 47.8 174.6 45.8z M169.4 60.1c-1.5 1.1-3.9 1.9-7.2 1.9H159V48h4.2c2.9 0 5 0.5 6.4 1.6c1.4 1.1 2 2.8 2 5.1	C171.7 57.1 170.9 59 169.4 60.1z",
			path3 :"M148.5 128h-41c-2.8 0-5.1 2.2-5.1 5c0 2.8 2.3 5 5.1 5h41c2.8 0 5.1-2.2 5.1-5 C153.7 130.2 151.4 128 148.5 128z M177.3 121.1l-27.4-22.9c-0.9-0.8-2-1.2-3.2-1.2H81.8c-2.8 0-4.8 2.3-4.8 5.1v107.7 c0 2.8 2 5.1 4.8 5.1h92.3c2.8 0 4.8-2.3 4.8-5.1v-84.8C179 123.5 178.5 122.1 177.3 121.1z M169 199.6c0 2.8-2.3 5.4-5.1 5.4 H92.1c-2.8 0-5.1-2.6-5.1-5.4v-87.2c0-2.8 2.3-5.4 5.1-5.4h52c1.2 0 2.3 0.5 3.3 1.3l19.9 16.4c1.2 1 1.9 2.5 1.9 4V199.6z M148.5 153h-41c-2.8 0-5.1 2.7-5.1 5.5c0 2.8 2.3 5.5 5.1 5.5h41c2.8 0 5.1-2.7 5.1-5.5 C153.7 155.7 151.4 153 148.5 153z M153 184c0-2.8-2.2-5-5-5h-41c-2.8 0-5 2.2-5 5l0 0c0 2.8 2.2 5 5 5h41 C150.8 189 153 186.8 153 184L153 184z",
			bubble: "M12.5 24.8L12.5 24.8c-6.8 0-12.3-5.5-12.3-12.2v0c0-6.8 5.5-12.2 12.2-12.2h0 c6.8 0 12.2 5.5 12.2 12.2v0C24.8 19.3 19.3 24.8 12.5 24.8z"
		}
	},
	epg : {
		options : {
			editable : {tools : ["edit"]},
			width : 55,
			height : 55,
			type : "epg",
			path : "M128 5C60.2 5 5 60.2 5 128s55.2 123 123 123s123-55.2 123-123S195.8 5 128 5z M128 245 c-64.6 0-117-52.4-117-117S63.4 11 128 11s117 52.4 117 117S192.6 245 128 245z",
			path2: "M98.9 88.5h-22V49.6h22V55H83.3v10.6h14.7v5.3H83.3v12.2h15.6V88.5z M138.1 61.4c0 4.1-1.3 7.2-4 9.3c-2.7 2.2-6.4 3.2-11.4 3.2h-4v14.6h-6.4V49.6h11.2 c4.9 0 8.5 1 10.9 3C136.9 54.6 138.1 57.5 138.1 61.4z M118.7 68.6h3.4c3.3 0 5.7-0.6 7.2-1.7c1.5-1.1 2.3-2.9 2.3-5.3 c0-2.2-0.7-3.9-2-5c-1.4-1.1-3.5-1.6-6.4-1.6h-4.4V68.6z M164.8 67.4h14.3v19.5c-2.3 0.8-4.6 1.3-6.7 1.6c-2.2 0.3-4.5 0.5-7 0.5c-5.9 0-10.4-1.7-13.6-5.2 c-3.2-3.5-4.8-8.4-4.8-14.8c0-6.3 1.8-11.2 5.4-14.7c3.6-3.5 8.6-5.3 14.9-5.3c4.1 0 7.9 0.8 11.5 2.3l-2.2 5.3 c-3.2-1.5-6.3-2.2-9.5-2.2c-4.2 0-7.4 1.3-9.8 3.9c-2.4 2.6-3.6 6.2-3.6 10.7c0 4.8 1.1 8.4 3.3 10.8c2.2 2.5 5.3 3.7 9.4 3.7 2.1 0 4.3-0.3 6.6-0.8v-10h-8.1V67.4z",
			path3: "M172 123H84v54h-8v7h8h88h8v-7h-8V123z M165 177H91v-47h74V177z"
		}
	},
	contract : {
		options : {
			editable : {tools : ["edit"]},
			width : 55,
			height : 55,
			type : "contract",
			path : "M128 5C60.2 5 5 60.2 5 128s55.2 123 123 123s123-55.2 123-123S195.8 5 128 5z M128 245 c-64.6 0-117-52.4-117-117S63.4 11 128 11s117 52.4 117 117S192.6 245 128 245z",
			path2: "M78.2 149.6c0.8-3.4 3.1-5.7 5.4-8.1c7.2-7.3 14.4-14.6 21.6-21.9c4.6-4.7 9.9-4.7 14.6 0 c1.2 1.2 2.4 2.5 4 4.2c1.4-1.7 2.6-3.2 4-4.5c4.2-4.3 9.7-4.3 13.9 0c8 8.1 16 16.2 24 24.4c4.3 4.4 4.3 9.9 0 14.3 c-2.3 2.3-4.5 4.6-7 7.1c1.6 1.5 3 2.8 4.4 4.3c2.5 2.6 2.5 5.6 0 8.2c-8.9 9-17.7 18.1-26.6 27.1c-2.7 2.7-6.3 2.8-9 0.1 c-1.2-1.2-2.4-2.5-3.9-4.2c-1.4 1.5-2.6 3-3.8 4.3c-2.6 2.6-6.1 2.6-8.7 0c-1.7-1.7-3.7-3.3-3.8-5.9c-4-1.4-6.9-3.8-8.2-8 c-0.1-0.4-0.7-0.8-1.1-0.9c-3.5-1.3-5.8-3.7-7.2-7.2c-0.2-0.5-0.7-1.1-1.2-1.2c-3.3-1.1-5.4-3.5-6.8-6.4c-1-2-0.2-4 1.3-5.6 c1.4-1.5 2.9-2.8 4.4-4.3c-3.4-4.5-8.7-7.3-10.1-13C78.2 151.3 78.2 150.5 78.2 149.6z M125.2 197.3c1.7 2.2 2.9 3.9 4.2 5.3 c1.5 1.6 3.3 1.6 4.9 0.1c1.3-1.2 2.6-2.5 4.3-4.1c-1.1-0.9-2.1-1.6-2.9-2.3c-2.1-2-4.1-4-6-6.2c-0.4-0.4-0.1-1.5-0.1-2.2 c0.7 0.1 1.4 0 2 0.3c0.5 0.2 0.8 0.7 1.2 1.1c2.4 2.5 4.8 4.9 7.1 7.2c2.2-2.2 4.2-4.3 6.4-.4c-0.1-0.1-0.2-0.3-0.4-0.4 c-2.6-2.6-5.2-5.2-7.6-7.9c-0.4-0.5-0.6-1.8-0.3-2.3c0.7-1 1.6-0.4 2.2 0.3c1.2 1.2 2.4 2.4 3.5 3.6c1.6 1.6 3.2 3.2 4.5 4.6 c2.2-2.2 4.2-4.2 6.3-6.3c-0.3-0.4-0.7-0.8-1.1-1.2c-2.3-2.3-4.5-4.6-6.8-6.9c-0.7-0.7-1.5-1.5-0.5-2.5c1-1 1.8-0.3 2.6 0.5 c0.8 0.9 1.7 1.7 2.5 2.6c1.8 1.9 3.7 3.7 5.7 5.8c1.3-1.4 2.4-2.7 3.6-3.9c2.1-2.2 2.1-3.2-0.1-5.4c-10-10.2-20-20.4-30-30.5 c-0.4-0.4-0.9-0.8-1.6-1.5c-1.2 1.3-2.2 2.6-3.3 3.7c-4.5 4.6-9 9.2-13.5 13.7c-2.1 2.1-4.6 2.7-7.4 1.8 c-2.6-0.9-4.3-2.8-4.8-5.6c-0.6-2.9 0.5-5.2 2.5-7.2c6-6.1 11.9-12.1 17.9-8.2c0.4-0.4 0.9-0.9 1.4-1.5 c-1.5-1.5-2.9-2.8-4.2-4.2c-3.1-3-6.7-3-9.8 0c-8 8.1-16 16.2-24 24.4c-3.4 3.4-3.4 7.1 0 10.4c2.4 2.4 5 4.6 7.9 7.3 c1.7-0.5 4.1-0.2 6.2 1.6c2.1 1.7 2.8 4.1 2.7 6.7c6 0.8 6.9 1.7 8.5 8.5c5.9 0.8 7.3 2.3 8.3 8.4 C123.5 189.3 126.2 192.7 125.2 197.3z M156.6 163c0.5-0.4 0.6-0.5 0.7-0.6c2.2-2.2 4.4-4.4 6.6-6.7c2.9-3 2.9-6.8-0.1-9.8 c-8.1-8.3-16.2-16.5-24.3-24.8c-3-3-6.7-3-9.6 0c-2.3 2.2-4.5 4.5-6.7 6.8c-6.3 6.4-12.6 12.8-18.9 19.2 c-1.2 1.3-2.1 2.7-1.7 4.5c0.4 1.8 1.5 3 3.2 3.5c1.8 0.5 3.3-0.1 4.5-1.4c6.1-6.1 12.1-12.3 18.2-18.5c0.4-0.4 0.7-0.9 1.2-1.1c0.5-0.2 1.2-0.2 1.6 0c0.3 0.2 0.6 1.1 0.4 1.4c-0.7 1.2 0.1 1.7 0.7 2.4c6.6 6.7 13.1 13.3 19.7 20C153.6 159.7 155 161.3 156.6 163z M97.6 170.9c0-3.7-3.6-6.2-6.4-4.3c-2.2 1.5-4 3.6-5.7 5.6c-1 1.1-0.4 2.5 0.7 3.3 c1.6 1.3 2.7 4.3 5.1 2.9c2.3-1.4 4-4 5.9-6.1C97.5 171.9 97.5 171.2 97.6 170.9z M105.8 179.5c0-2.3-0.8-3.7-2.3-4.5 c-1.5-0.8-3.2-0.9-4.5 0.3c-1.8 1.6-3.5 3.3-5.1 5.1c-1.2 1.5-0.4 2.9 0.8 3.9c1.5 1.3 2.7 4.1 5.2 2.3c2-1.4 3.5-3.4 5.2-5.2 C105.6 180.8 105.7 179.9 105.8 179.5z M115.6 203.8c0.2-0.1 0.9-0.1 1.2-0.4c1.8-1.8 3.7-3.4 5.2-5.5c1.3-1.8 0.5-4.3-1.2-5.6 c-1.7-1.3-3.9-1.3-5.6 0.2c-1.5 1.4-3 2.9-4.4 4.5c-0.4 0.5-0.8 1.3-0.8 1.9C110.1 200.6 113.6 203.8 115.6 203.8z M114.3 187.6 c-0.2-1.9-0.9-3.4-2.7-4.3c-1.7-0.8-3.4-0.6-4.8 0.7c-1.3 1.2-2.4 2.5-3.7 3.7c-1 1-2.2 2.2-1.2 3.6c1.3 1.8 2.6 4 5.2 4.3 c0.2 0 0.5-0.1 0.7-0.2c2-2.1 4.1-4.2 6-6.3C114.2 188.8 114.1 188.1 114.3 187.6z M115.3 206.8c-1.6 0-3.1-0.7-4.4-2c-0.2-0.2-0.4-0.4-0.7-0.6c-1.5-1.5-3.1-3-3.1-5.3 c-4.4-1.6-7-4.1-8.2-8c-0.1-0.3-0.6-0.7-1-0.9c-3.5-1.3-5.9-3.7-7.2-7.3c-0.2-0.5-0.7-1-1.1-1.2c-2.9-0.9-5.2-3.1-6.9-6.5 c-0.9-1.9-0.5-3.9 1.3-5.7c1-1 2-2 3-3c0.4-0.4 0.9-0.8 1.3-1.3c-1.2-1.5-2.6-2.9-3.9-4.2c-2.7-2.6-5.2-5-6.2-8.8l0-2.6l0 0 c0.8-3.4 3.1-5.8 5.4-8.1l0.1-0.1c4.8-4.8 9.6-9.7 14.2-14.5c2.4-2.5 4.9-5 7.3-7.5c4.7-4.7 10-4.7 14.7 0 c0.8 0.8 1.6 1.7 2.5 2.6c0.4 0.5 0.9 1 1.4 1.5c0.4-0.5 0.9-1 1.3-1.5c0.9-1.1 1.7-2.1 2.6-3c4.3-4.3 9.8-4.3 14 0 c7.4 7.5 15.2 15.4 24 24.4c4.4 4.4 4.4 10 0 14.4c-1.5 1.6-3.1 3.1-4.7 4.8c-0.7 0.7-1.5 1.5-2.2 2.2c0.5 0.5 0.9 0.9 1.4 1.3 c1 1 2 1.9 2.9 2.9c2.5 2.6 2.5 5.7 0 8.3c-8.8 9-17.8 18.1-26.6 27.1c-2.7 2.8-6.4 2.8-9.2 0.1c-0.8-0.8-1.6-1.7-2.5-2.6 c-0.4-0.5-0.9-0.9-1.3-1.4c-0.4 0.5-0.8 0.9-1.2 1.4c-0.9 1-1.7 1.9-2.6 2.8C118.4 206.1 116.8 206.8 115.3 206.8z M78.3 149.6 v2.5c0.9 3.7 3.4 6.1 6.1 8.7c1.4 1.3 2.8 2.7 4 4.3l0.1 0.1l-0.1 0.1c-0.5 0.4-0.9 0.9-1.4 1.3c-1 1-2 2-3 3 c-1 1-2.5 3.1-1.3 5.5c1.7 3.4 3.9 5.5 6.8 6.4c0.5 0.2 1.1 0.7 1.3 1.3c1.3 3.6 3.7 5.9 7.1 7.2c0.5 0.2 1 0.6 1.1 1 c1.2 3.8 3.7 6.3 8.2 7.9l0.1 0l0 0.1c0 2.2 1.5 3.7 3.1 5.2c0.2 0.2 0.4 0.4 0.7 0.6c2.6 2.6 6 2.6 8.6 0 c0.9-0.9 1.7-1.8 2.6-2.8c0.4-0.5 0.8-0.9 1.3-1.4l0.1-0.1l0.1 0.1c0.5 0.5 1 1 1.4 1.5c0.9 1 1.7 1.8 2.5 2.6 c2.7 2.7 6.3 2.6 8.9-0.1c8.8-9 17.8-18.1 26.6-27.1c2.5-2.5 2.5-5.5 0-8c-0.9-1-1.9-1.9-2.9-2.9c-0.5-0.5-1-0.9-1.5-1.4 l-0.1-0.1l0.1-0.1c0.8-0.8 1.5-1.6 2.3-2.3c1.6-1.6 3.2-3.2 4.7-4.8c4.2-4.4 4.2-9.8 0-14.1c-8.8-8.9-16.6-16.9-24-24.4 c-4.2-4.2-9.6-4.2-13.8 0c-0.9 0.9-1.7 1.9-2.6 3c-0.4 0.5-0.9 1-1.3 1.6l-0.1 0.1l-0.1-0.1c-0.5-0.6-1-1.1-1.5-1.6 c-0.9-1-1.7-1.8-2.5-2.6c-4.6-4.7-9.8-4.7-14.4 0c-2.5 2.5-4.9 5-7.3 7.5c-4.7 4.7-9.5 9.7-14.2 14.5l-0.1 0.1 C81.3 144 79.1 146.3 78.3 149.6z M131.9 203.9c-0.9 0-1.7-0.4-2.5-1.2c-0.9-1-1.8-2.1-2.7-3.4c-0.5-0.6-1-1.3-1.5-2l0 0l0 0 c0.5-2 0.2-3.8-0.8-5.2c-1.3-1.9-3.7-3.1-7.1-3.6l-0.1 0l0-0.1c-1-6-2.4-7.5-8.2-8.3l-0.1 0l0-0.1c-1.6-6.7-2.4-7.6-8.4-8.4 l-0.1 0l0-0.1c0.1-2.9-0.8-5-2.7-6.6c-2.2-1.8-4.6-2-6.1-1.5l-0.1 0l0 0c-0.9-0.8-1.8-1.6-2.7-2.4c-1.8-1.7-3.6-3.2-5.2-4.9 c-1.7-1.7-2.5-3.4-2.6-5.2c0-1.8 0.8-3.6 2.6-5.4c8.5-8.6 16.3-16.6 24-24.4c3.1-3.1 6.8-3.1 9.9 0c0.9 0.9 1.9 1.8 2.8 2.8 l1.5 1.4l-0.1 0.1c-0.2 0.2-0.3 0.4-0.5 0.5c-0.3 0.4-0.6 0.7-0.9 1l-3.9 4c-4.6 4.7-9.3 9.5-14 14.2c-2.2 2.2-3 4.5-2.4 7.1 c0.5 2.7 2.2 4.6 4.8 5.5c2.7 0.9 5.2 0.3 7.3-1.7c4.6-4.6 9.2-9.3 13.5-13.7c0.8-0.8 1.5-1.6 2.2-2.5c0.4-.7-0.8 1.1-1.2 l0.1-0.1l0.1 0.1c0.2 0.2 0.5 0.4 0.7 0.6c0.4 0.3 0.6 0.6 0.9 0.9c10 10.2 20 20.4 30 30.5c2.2 2.3 2.3 3.3 0.1 5.6 c-0.8 0.8-1.6 1.7-2.4 2.5c-0.4 0.4-0.8 0.9-1.2 1.3l-0.1 0.1l-1.4-1.4c-1.5-1.6-3-3-4.4-4.5c-0.3-0.3-0.6-0.6-0.9-0.9 c-0.5-0.6-1.1-1.1-1.6-1.7c-0.4-0.5-1-1-1.5-1c0 0 0 0 0 0c-0.3 0-0.6 0.1-0.9 0.5c-.3 0.3-0.4 0.6-0.4 0.8c0 0.5 0.5 1 0.9 1.5 l0 0c1.2 1.2 2.3 2.4 3.5 3.5c1.1 1.1 2.2 2.3 3.3 3.4c0.3 0.3 0.6 0.6 0.8 0.9c0.1 0.1 0.2 0.2 0.3 0.3l0.1 0.1l-0.1 0.1l-6.4 6.4l-5.6-5.7c-0.9-0.9-1.7-1.7-2.6-2.6c-0.6-0.6-1-0.8-1.4-0.8c-0.3 0-0.5 0.2-0.7 0.5c-0.3 0.4-0.1 1.7 0.3 2.2c1.8 2 3.7 3.9 5.6 5.8c0.7 0.7 1.4 1.4 2.1 2.1c0.1 0.1 0.2 0.3 0.3 0.4c0 0 0 0.1 0.1 0.1l0.1 0.1l-6.5 6.5l-1.9-1.9c-1.7-1.7-3.5-3.5-5.2-5.4c-0.1-0.1-0.2-0.3-0.3-0.4c-0.2-0.3-0.5-0.6-0.8-0.7c-0.4-0.2-0.9-0.2-1.4-0.2c-0.2 0-0.3 0-0.5 0 c0 0.2 0 0.4 0 0.7c0 0.5-0.1 1.1 0.1 1.4c1.8 2.1 3.8 4 6 6.2c0.5 0.5 1.1 1 1.8 1.5c0.3 0.3 0.7 0.5 1.1 0.8l0.1 0.1l-1.6 1.6c-1 1-1.9 1.8-2.8 2.6C133.6 203.5 132.7 203.9 131.9 203.9z M125.3 197.3c0.5 0.7 1 1.3 1.5 1.9c1 1.3 1.8 2.4 2.7 3.4 c1.4 1.5 3.2 1.6 4.8 0.1c0.9-0.8 1.8-1.7 2.8-2.6l1.5-1.4c-0.3-0.3-0.7-0.5-1-0.8c-0.7-0.5-1.3-1-1.8-1.5 c-2.2-2.2-4.2-4.1-6-6.2c-0.3-0.3-0.2-0.9-0.2-1.5c0-0.3 0-0.5 0-0.8l0-0.1l0.1 0c0.2 0 0.4 0 0.6 0c0.5 0 1 0.1 1.4 0.2 c0.3 0.1 0.6 0.5 0.9 0.8c0.1 0.1 0.2 0.3 0.3 0.4c1.8 1.8 3.5 3.6 5.2 5.4l1.8 1.8l6.2-6.2c0 0 0 0 0 0c-0.1-0.1-0.2-0.2-0.3-0.3c-0.7-0.7-1.4-1.4-2.1-2.1c-1.9-1.9-3.8-3.8-5.6-5.8c-0.5-0.5-0.7-1.9-0.3-2.4c0.2-0.3 0.5-0.5 0.8-0.5 c0.5 0 1 0.2 1.6 0.9c0.9 0.9 1.7 1.7 2.6 2.6l5.4 5.5l6.2-6.2c-0.1-0.1-0.2-0.2-0.2-0.3c-0.2-0.3-0.5-0.6-0.8-0.9 c-1.1-1.1-2.2-2.3-3.3-3.4c-1.2-1.2-2.3-2.4-3.5-3.5l0 0c-0.5-0.5-1-1-1-1.6c0-0.3 0.2-0.7 0.5-1c0.3-0.3 0.7-0.5 1-0.5 c0 0 0 0 0 0c0.6 0 1.2 0.5 1.7 1c0.5 0.6 1.1 1.1 1.6 1.7c0.3 0.3 0.6 0.6 0.9 0.9c1.4 1.5 2.9 2.9 4.4 4.5l1.2 1.2 c0.4-0.4 0.8-0.9 1.2-1.3c0.8-0.9 1.6-1.7 2.4-2.6c2.1-2.2 2.1-3.1-0.1-5.3c-10-10.2-20-20.4-30-30.5c-0.3-0.3-0.5-0.5-0.9-0.9 c-0.2-0.2-0.4-0.3-0.6-0.5c-0.3 0.4-0.7 0.8-1 1.2c-0.8 0.9-1.5 1.7-2.2 2.5c-4.3 4.4-8.9 9.1-13.5 13.7 c-2.1 2.1-4.7 2.7-7.5 1.8c-2.6-0.9-4.3-2.8-4.9-5.6c-0.5-2.7 0.3-5 2.5-7.3c4.7-4.7 9.4-9.6 14-14.2l3.9-4 c0.3-0.3 0.6-0.6 0.9-1c0.1-0.2 0.3-0.3 0.4-0.5l-1.3-1.3c-1-1-1.9-1.9-2.8-2.8c-3-3-6.6-3-9.6 0c-7.7 7.8-15.5 15.7-24 24.4 c-1.7 1.7-2.5 3.5-2.5 5.2c0 1.7 0.9 3.4 2.5 5.1c1.7 1.6 3.4 3.2 5.2 4.8c0.8 0.8 1.7 1.5 2.6 2.4c1.6-0.4 4-0.2 6.2 1.6c1.9 1.6 2.8 3.7 2.7 6.6c5.9 0.8 6.9 1.8 8.5 8.5c5.8 0.8 7.3 2.3 8.3 8.4c3.4 0.5 5.9 1.7 7.2 3.6C125.5 193.4 125.7 195.2 125.3 197.3z M115.6 203.9L115.6 203.9c-2 0-5.6-3.2-5.7-4.9c0-0.6 0.3-1.5 0.8-2c1.3-1.5 2.8-3 4.4-4.5c1.6-1.5 3.9-1.6 5.7-0.2c1.7 1.3 2.6 3.9 1.2 5.8c-1.2 1.6-2.7 3-4.1 4.4c-0.4 0.3-0.7 0.7-1.1 1 c-0.3 0.3-0.7 0.3-1 0.4C115.7 203.9 115.7 203.9 115.6 203.9L115.6 203.9z M118 191.4c-1 0-2 0.4-2.8 1.2 c-1.6 1.5-3.1 3-4.4 4.5c-0.4 0.5-0.7 1.3-0.7 1.9c0 1.7 3.5 4.7 5.4 4.7c0.1 0 0.1 0 0.2 0c0.3 0 0.7-0.1 0.9-0.3 c0.4-0.3 0.7-0.7 1.1-1c1.4-1.4 2.9-2.8 4.1-4.4c1.3-1.8 0.4-4.3-1.2-5.5C119.8 191.8 118.9 191.4 118 191.4z M107.3 195.8C107.2 195.8 107.2 195.8 107.3 195.8c-2.5-0.3-3.7-2.1-4.9-3.8c-0.1-0.2-0.3-0.4-0.4-0.6c-1.1-1.5 0.2-2.8 1.2-3.7 c0.6-0.5 1.1-1.1 1.7-1.7c0.7-0.7 1.3-1.4 2-2c1.4-1.3 3.1-1.6 4.9-0.8c1.6 0.8 2.5 2.2 2.7 4.3l0 0l0 0c0 0.1-0.1 0.3-0.1 0.5 c-0.1 0.4-0.1 0.8-0.3 1.1c-1.8 2-3.7 4-5.6 5.9l-0.4 0.4C107.8 195.7 107.5 195.8 107.3 195.8z M109.6 183c-1 0-1.9 0.4-2.7 1.2c-0.7 0.7-1.4 1.3-2 2c-0.5 0.6-1.1 1.1-1.7 1.7c-0.9 0.9-2.1 2.1-1.1 3.4c0.1 0.2 0.3 0.4 0.4 0.6c1.2 1.7 2.4 3.4 4.7 3.7 c0.2 0 0.5-0.1 0.6-0.2l0.4-0.4c1.9-1.9 3.8-3.9 5.6-5.9c0.2-0.2 0.2-0.6 0.3-1c0-0.2 0-0.3 0.1-0.5c-0.2-2.1-1-3.4-2.6-4.2C110.9 183.2 110.3 183 109.6 183z M98.4 187.3c-1.1 0-1.9-0.9-2.7-1.8c-0.3-0.4-0.7-0.8-1-1.1c-0.9-0.8-1.4-1.6-1.5-2.3 c-0.1-0.6 0.2-1.2 0.6-1.7c1.4-1.7 3.1-3.4 5.1-5.1c1.3-1.1 2.9-1.2 4.6-0.3c1.6 0.9 2.4 2.3 2.4 4.6l0 0c0 0.1-0.1 0.2-0.1 0.4c-0.1 0.4-0.2 1.1-0.6 1.5c-0.3 0.3-0.5 0.6-0.8 0.9c-1.3 1.6-2.7 3.2-4.4 4.3C99.4 187.2 98.9 187.3 98.4 187.3z M101.3 174.5c-0.8 0-1.6 0.3-2.2 0.9c-1.9 1.7-3.6 3.4-5 5.1c-0.4 0.5-0.6 1-0.6 1.6c0.1 0.7 0.5 1.5 1.4 2.2c0.4 0.3 0.7 0.7 1.1 1.1c1.1 1.3 2.2 2.5 4 1.2c1.6-1.1 3-2.7 4.3-4.3c0.3-0.3 0.5-0.6 0.8-0.9c0.4-0.4 0.5-1 0.6-1.5c0-0.1 0.1-0.3 0.1-0.4 c0-2.2-0.7-3.6-2.3-4.4C102.7 174.7 102 174.5 101.3 174.5z M90 178.9c-1.1 0-1.9-1-2.7-2c-0.4-0.5-0.8-1-1.2-1.3 c-0.7-0.6-1.1-1.3-1.2-2c0-0.5 0.1-1 0.5-1.5c1.7-2 3.5-4.1 5.8-5.6c1.2-0.8 2.6-0.9 4-0.1c1.6 0.9 2.6 2.6 2.6 4.6l0 0 c0 0.1 0 0.1 0 0.2c0 0.3-0.1 0.8-0.4 1.2c-0.5 0.5-1 1.1-1.4 1.7c-1.4 1.6-2.8 3.3-4.5 4.4C90.8 178.8 90.4 178.9 90 178.9zM93.2 166.1c-0.7 0-1.4 0.2-2 0.6c-2.2 1.5-4 3.6-5.7 5.6c-0.3 0.4-0.5 0.8-0.4 1.3c0.1 0.7 0.5 1.3 1.1 1.9 c0.4 0.4 0.8 0.8 1.2 1.3c1.1 1.3 2.1 2.6 3.8 1.5c1.7-1.1 3.1-2.7 4.5-4.4c0.5-0.6 1-1.2 1.4-1.7c0.2-0.3 0.3-0.7 0.3-1 c0-0.1 0-0.2 0-0.3c0-1.9-1-3.5-2.5-4.4C94.4 166.2 93.8 166.1 93.2 166.1z M156.6 163.1L156.6 163.1c-0.5-0.6-1-1.1-1.5-1.6c-1-1.1-2-2.2-3-3.2c-4.8-4.9-9.7-9.9-14.4-14.7l-5.2-5.3c-0.1-0.1-0.2-0.2-0.2-0.2c-0.6-0.6-1.2-1.2-0.5-2.3 c0.1-0.1 0.1-0.3 0-0.6c-0.1-0.3-0.2-0.6-0.4-0.7c-0.4-0.2-1.1-0.2-1.5 0c-0.3 0.1-0.6 0.4-0.8 0.7c-0.1 0.1-0.2 0.2-0.3 0.3 c-2.1 2.2-4.3 4.4-6.4 6.6c-3.9 4-7.8 7.9-11.7 11.9c-1.4 1.4-2.9 1.9-4.6 1.4c-1.8-0.5-2.9-1.7-3.3-3.6 c-0.4-1.6 0.2-3.1 1.7-4.6c4.6-4.7 9.3-9.5 13.9-14.1l5-5.1c0.7-0.7 1.3-1.4 2-2.1c1.5-1.6 3.1-3.2 4.7-4.7c3-3 6.8-3 9.8 0 c8.4 8.5 16.6 16.9 24.3 24.8c3 3.1 3 6.9 0.1 9.9c-1.6 1.6-3.2 3.3-4.7 4.8c-0.6 0.6-1.2 1.2-1.8 1.8c-0.1 0.1-0.2 0.1-0.3 0.3 C156.9 162.8 156.8 162.9 156.6 163.1L156.6 163.1z M130.5 134.1c0.3 0 0.6 0.1 0.8 0.2c0.2 0.1 0.4 0.5 0.5 0.8 c0 0.1 0.1 0.5 0 0.7c-0.6 1-0.1 1.5 0.5 2.1c0.1 0.1 0.2 0.2 0.2 0.2l5.2 5.3c4.7 4.8 9.6 9.8 14.4 14.7c1 1.1 2 2.1 3 3.2c0.5 0.5 0.9 1 1.4 1.5c0.1-0.1 0.3-0.2 0.4-0.3c0.2-0.1 0.2-0.2 0.3-0.3c0.6-0.6 1.2-1.2 1.8-1.8c1.6-1.6 3.2-3.2 4.7-4.8 c2.9-3 2.8-6.7-0.1-9.7c-7.7-7.9-15.9-16.2-24.3-24.8c-2.9-2.9-6.6-3-9.5 0c-1.6 1.6-3.1 3.2-4.7 4.7c-0.7 0.7-1.3 1.4-2 2.1 l-5 5.1c-4.5 4.6-9.3 9.4-13.9 14.1c-1.5 1.5-2 2.9-1.6 4.4c0.4 1.8 1.5 2.9 3.2 3.4c1.6 0.5 3.1 0 4.4-1.3 c3.9-4 7.8-7.9 11.7-11.9c2.1-2.2 4.3-4.4 6.4-6.6c0.1-0.1 0.2-0.2 0.3-0.3c0.3-0.3 0.6-0.6 0.9-0.8 C129.9 134.1 130.2 134.1 130.5 134.1z",
			path3: "M73.6 57.7c3.1 0 5.6 0.7 7.4 1.5l1.5-5.5C81.1 53 77.9 52 73.3 52c-11.6 0-20.3 7.5-20.3 20 c0 11.6 7.3 19.1 19.3 19.1c4.6 0 8.2-0.9 9.8-1.7L81.1 84c-1.9 0.8-4.8 1.3-7.5 1.3c-8.4 0-13.2-5.3-13.2-13.6 C60.4 62.6 65.9 57.7 73.6 57.7z M110.5 52c-10.7 0-18 8.2-18 19.9c0 11.1 6.7 19.3 17.5 19.3c10.4 0 18.1-7.2 18.1-19.9 C128 60.3 121.5 52 110.5 52z M110.3 85.5c-6.6 0-10.5-6.1-10.5-13.9c0-7.8 3.6-14.2 10.5-14.2c6.9 0 10.4 6.7 10.4 13.9C120.7 79.3 116.9 85.5 110.3 85.5z M164 65.8c0 5.8-0.3 11.2 0.2 16.2h-0.1c-1.7-5-4-9-6.5-13.2L147.9 52H141v38h6V76.9 c0-6.2-0.4-11.2-0.7-16.1l0.1-0.1c1.9 4.3 4.2 8.6 6.7 12.8l9.8 16.4h7.2V52h-6V65.8z M183.5 90h4.5c0.4 0 0.7-0.3 0.7-0.7v-4.5c0-0.4-0.3-0.7-0.7-0.7h-4.5c-0.4 0-0.7 0.3-0.7 0.7v4.5 C182.8 89.7 183.1 90 183.5 90z M199.7 90h4.5c0.4 0 0.7-0.3 0.7-0.7v-4.5c0-0.4-0.3-0.7-0.7-0.7h-4.5c-0.4 0-0.7 0.3-0.7 0.7v4.5C199 89.7 199.3 90 199.7 90z"
		}
	},
	l3out : {
		options : {
			editable : {tools : ["edit"]},
			width : 55,
			height : 55,
			type : "l3out",
			path : "M128 5C60.2 5 5 60.2 5 128s55.2 123 123 123s123-55.2 123-123S195.8 5 128 5z M128 245 c-64.6 0-117-52.4-117-117S63.4 11 128 11s117 52.4 117 117S192.6 245 128 245z",
		    path2: "M102.6 57h-4.3c-0.2 0-0.4 0.2-0.4 0.4v38.3c0 0.2 0.2 0.4 0.4 0.4h24.3c0.2 0 0.4-0.2 0.4-0.4 v-3.3c0-0.2-0.2-0.4-0.4-0.4h-19.3c-0.2 0-0.4-0.2-0.4-0.4V57.4C103 57.2 102.8 57 102.6 57z M157.3 80.1c-0.5-1.1-1.2-2.1-2.1-2.8c-0.8-0.7-1.5-1.2-2.6-1.7c-1.1-0.4-2.7-0.7-2.7-0.9v-0.4 c2-0.4 3.7-1.4 5.2-3.1c1.5-1.6 2.2-3.5 2.2-5.7c0-1.6-0.4-3-1-4.2c-0.6-1.2-1.6-2.2-2.9-3.1c-1.2-0.8-2.5-1.3-4-1.7 c-1.5-0.4-3.1-0.5-5.1-0.5c-1.9 0-4.2 0.3-6.3 0.8c-2.1 0.5-3.2 1.1-5.2 1.7V64h0.7c1.7-1 3.4-1.9 5.3-2.5s3.6-0.9 5.3-0.9 c1.1 0 2.1 0.1 2.9 0.3c0.9 0.2 1.7 0.5 2.4 1c0.7 0.5 1.3 1 1.7 1.8c0.4 0.7 0.6 1.5 0.6 2.5c0 2.1-0.8 3.9-6.4 1.8H141v4h2.8c1.3 0 2.5 0.1 3.6 0.3c1.1 0.2 2.1 0.6 2.9 1.1c0.8 0.5 1.4 1.3 1.9 2.2 0.4 0.9 0.7 2.1 0.7 3.5c0 1.4-0.2 2.6-0.6 3.5c-0.4 0.9-1 1.8-1.8 2.6c-0.8 0.7-1.8 1.3-3 1.6c-1.2 0.4-2.4 0.6-3.5 0.6 c-1.9 0-3.8-0.3-5.8-1c-2-0.6-3.8-1.4-5.3-2.4H132v5.5c2 0.6 3.3 1.2 5.4 1.7c2.1 0.5 4.3 0.8 6.5 0.8c2.1 0 4.1-0.3 5.9-0.9 c1.8-0.6 3.3-1.5 4.7-2.7c1.2-1.1 2.1-2.4 2.7-3.9c0.6-1.5 1-3.1 1-4.9C158.2 82.7 157.9 81.3 157.3 80.1z",
			path3: "M152.1 163c0.5 5 0.7 3.3 0.7 5.3c0 13.6-11.4 24.5-25.4 24.5c-14 0-27.6-11.2-27.6-24.9 c0-13.6 9.2-24.9 23.2-24.9v-7.9c-19 0-31.4 14.7-31.4 32.7c0 18 16.2 32.7 34.7 32.7c18.5 0 34.2-14.1 34.2-32.1 c0-2 0.4-0.3 0-5.3H152.1z M149 131l-5.5 4h12.6l-8.3 7.4c0 0 0-0.3 0-0.3l-5.8 5.5l-9.5 9.2l4 3.9l9.7-9.5l5.8-5.6l8-7.8 l-0.2 12.2l5.2-5.3V131H149z",
			common: "M130.2 112.1c-15.7 0-29.6 8.5-36.8 21.6l-10.3-10.3v29.2 h29.2l-12.6-12.6c5.8-11.2 17.1-18.9 30.5-18.9c18.4 0 33.7 15.3 33.7 33.7s-15.3 33.7-33.7 33.7c-14.8 0-26.9-9.4-31.9-22.5 h-9.4c4.9 18 21.6 31.4 41.3 31.4c23.8 0 42.7-19.3 42.7-42.7S153.6 112.1 130.2 112.1z M139.1 155.1c0 0.4 0 0.8 0 1.1c0 0.3-0.1 0.6-0.1 0.8c0 0.2-0.1 0.4-0.2 0.5 c-0.1 0.1-0.2 0.2-0.2 0.3l-15.1 9.7c-0.2 0.1-0.3 0.2-0.5 0.2c-0.1 0-0.2-0.1-0.3-0.3s-0.1-0.4-0.2-0.8c0-0.3 0-0.8 0-1.3 c0-0.7 0-1.3 0-1.7c0-0.4 0.1-0.7 0.1-1c0.1-0.2 0.1-0.4 0.2-0.5c0.1-0.1 0.2-0.2 0.4-0.3l11.3-6.9l-11.1-6.7c-0.2-0.1-0.4-0.2-0.5-0.4c-0.1-0.1-0.2-0.3-0.3-0.5c-0.1-0.2-0.1-0.6-0.1-1c0-0.4 0-1 0-1.7c0-0.6 0-1.1 0-1.5 c0-0.4 0.1-0.6 0.2-0.8s0.2-0.2 0.3-0.2s0.3 0.1 0.5 0.2l15.1 9.8c0.2 0.1 0.3 0.4 0.4 0.8C139.1 153.6 139.1 154.3 139.1 155.1z"

		}
	},
	vrf : {
		options : {
			editable : {tools : ["edit"]},
			width : 55,
			height : 55,
			type : "vrf",
			path : "M128 5C60.2 5 5 60.2 5 128s55.2 123 123 123s123-55.2 123-123S195.8 5 128 5z M128 245 c-64.6 0-117-52.4-117-117S63.4 11 128 11s117 52.4 117 117S192.6 245 128 245z",
            path2 : "M88.3 90.8l-12-33.6c0-0.1-0.1-0.2-0.2-0.2h-5c-0.2 0-0.3 0.2-0.2 0.3l14.1 38.5 c0 0.1 0.1 0.2 0.2 0.2h6.6c0.1 0 0.2-0.1 0.2-0.2L106 57.3c0.1-0.2-0.1-0.3-0.2-0.3H101c-0.1 0-0.2 0.1-0.2 0.2l-12 33.6 C88.7 91 88.4 91 88.3 90.8z M141 74.6c1.4-1.9 2.1-4.3 2.1-7.1c0-2.1-0.4-3.8-1.2-5.2s-1.9-2.5-3.3-3.4 c-1.3-0.8-2.7-1.3-4.3-1.6c-1.6-0.3-3.6-0.4-5.9-0.4H117v39h6V80h7l13.1 16h6.8l-14.7-17.1C137.6 77.9 139.6 76.5 141 74.6z M127.9 76H123V61h5.8c1.4 0 2.6 0.3 3.7 0.5c1 0.2 1.9 0.6 2.7 1.1c0.9 0.6 1.6 1.4 2 2.2c0.4 0.9 0.6 1.9 0.6 3 c0 1.5-0.2 2.7-0.6 3.7c-0.4 1-1 1.8-1.7 2.5c-0.9 0.8-1.9 1.3-3.1 1.6C131 75.9 129.6 76 127.9 76z M185 60.6v-3.1c0-0.2-0.2-0.4-0.4-0.4h-24.1c-0.2 0-0.4 0.2-0.4 0.4v38.1c0 0.2 0.2 0.4 0.4 0.4 h5.1c0.2 0 0.4-0.2 0.4-0.4V77.4c0-0.2 0.2-0.4 0.4-0.4h16.1c0.2 0 0.4-0.2 0.4-0.4v-3.1c0-0.2-0.2-0.4-0.4-0.4h-16.1 c-0.2 0-0.4-0.2-0.4-0.4V61.4c0-0.2 0.2-0.4 0.4-0.4h18.1C184.8 61 185 60.8 185 60.6z",
			path3: "M88.2 159.3l12.8 12.5c0.1 0.1 0 0.3-0.1 0.3H65.2c-0.1 0-0.2 0.1-0.2 0.2v11.7 c0 0.1 0.1 0.2 0.2 0.2h35.6c0.1 0 0.2 0.2 0.1 0.3l-12.7 12.3c-0.1 0.1 0 0.3 0.1 0.3l11.1-1.2c0 0 0.1 0 0.1 0l17.4-18.1c0.1-0.1 0.1-0.2 0-0.2L99.5 159c0 0-0.1 0-0.1 0H88.3C88.1 159 88.1 159.2 88.2 159.3z M189.8 172H154c-0.1 0-0.2-0.2-0.1-0.3l12.8-12.7c0.1-0.1 0-0.3-0.1-0.3l-11.2 0c0 0-0.1 0-0.1 0l-17.4 18.5c-0.1 0.1-0.1 0.2 0 0.2l17.4 18.5c0 0 0.1 0 0.1 0h11.1c0.1 0 0.2-0.2 0.1-0.3l-12.8-11.5c-0.1-0.1 0-0.3 0.1-0.3 h35.9c0.1 0 0.2-0.1 0.2-0.2v-11.7C190 172.1 189.9 172 189.8 172z M147.7 132.9l-13.5 12.9c-0.1 0.1-0.3 0-0.3-0.1v-33.5c0-0.1-0.1-0.2-0.2-0.2h-11.7 c-0.1 0-0.2 0.1-0.2 0.2v33.4c0 0.1-0.2 0.2-0.3 0.1L109.2 133c-0.1-0.1-0.3 0-0.3 0.1l1.1 11.1c0 0 0 0.1 0 0.1l18.9 17.4 c0.1 0.1 0.2 0.1 0.2 0l18.9-17.4c0 0 0.1-0.1 0.1-0.1V133C148 132.9 147.8 132.8 147.7 132.9z",
			common: "M96.3 134.3l-5 4.9c-0.1 0.1-0.1 0.3 0 0.4l17 17.4c0.1 0.1 0.1 0.3 0 0.4l-17.4 17 c-0.1 0.1-0.1 0.3 0 0.4l4.9 5c0.1 0.1 0.3 0.1 0.4 0l22.9-22.3c0.1-0.1 0.1-0.3 0-0.4l-22.3-22.9 C96.5 134.2 96.4 134.2 96.3 134.3z M165.3 140.2l-4.9-5c-0.1-0.1-0.3-0.1-0.4 0l-22.9 22.3c-0.1 0.1-0.1 0.3 0 0.4l22.3 22.9 c0.1 0.1 0.3 0.1 0.4 0l5-4.9c0.1-0.1 0.1-0.3 0-0.4l-17-17.4c-0.1-0.1-0.1-0.3 0-0.4l17.4-17 C165.4 140.5 165.4 140.3 165.3 140.2z M150 124.3l-5.1-4.9c-0.1-0.1-0.3-0.1-0.4 0L127.7 137c-0.1 0.1-0.3 0.1-0.4 0l-17.6-16.9 c-0.1-0.1-0.3-0.1-0.4 0l-4.9 5.1c-0.1 0.1-0.1 0.3 0 0.4l23 22.1c0.1 0.1 0.3 0.1 0.4 0l22.1-23 C150.1 124.6 150.1 124.4 150 124.3z M103.7 189l5.1 4.9c0.1 0.1 0.3 0.1 0.4 0l16.9-17.6c0.1-0.1 0.3-0.1 0.4 0l17.6 16.9c0.1 0.1 0.3 0.1 0.4 0l4.9-5.1c0.1-0.1 0.1-0.3 0-0.4l-23-22.1c-0.1-0.1-0.3-0.1-0.4 0l-22.1 23C103.6 188.7 103.6 188.9 103.7 189z",
			common1: "M25 48.5L25 48.5C12 48.5 1.5 38 1.5 25v0C1.5 12 12 1.5 25 1.5h0C38 1.5 48.5 12 48.5 25v0 C48.5 38 38 48.5 25 48.5z",
			common2: "M9 19.4h1.5c0 0 0 0 0 0l1.8 6c0.4 1.3 0.7 2.4 1.1 3.7h0.1c0.4-1.3 0.7-2.4 1.1-3.7l1.8-6c0 0 0 0 0 0h1.4l-3.5 11.1c0 0 0 0 0 0h-1.6c0 0 0 0 0 0L9 19.4z M28.1 30.5l-2.6-4.6c0 0 0 0-0.1 0h-1.9c0 0-0.1 0-0.1 0.1v4.6H22V19.5c0 0 0-0.1 0.1-0.1h3.4c2.3 0 3.9 0.8 3.9 3.1c0 1.7-0.9 2.7-2.4 3.1c-0.1 0-0.1 0.1-0.1 0.1l2.8 4.8L28.1 30.5C28.1 30.6 28.1 30.6 28.1 30.5zM23.4 24.6C23.4 24.7 23.4 24.7 23.4 24.6l1.9 0.1c1.7 0 2.7-0.7 2.7-2.2c0-1.5-1-2-2.7-2h-1.8c0 0-0.1 0-0.1 0.1V24.6z M34.6 19.5C34.6 19.4 34.6 19.4 34.6 19.5l6.4 0v1.2h-5c0 0 0 0 0 0v3.7c0 0 0 0 0 0h4.2v1.2H36c0 0 0 0 0 0v5h-1.4V19.5z"

		}
	},
	bd : {
		options : {
			editable : {tools : ["edit"]},
			width : 55,
			height : 55,
			type : "bd",
			path : "M128 5C60.2 5 5 60.2 5 128s55.2 123 123 123s123-55.2 123-123S195.8 5 128 5z M128 245 c-64.6 0-117-52.4-117-117S63.4 11 128 11s117 52.4 117 117S192.6 245 128 245z",
			path2: "M119.8 84c0 1.9-0.4 3.7-1.1 5.2c-0.7 1.5-1.7 2.7-3 3.7c-1.5 1.2-3.1 2-6.7 0.7H90.3V56.9h11.6c2.9 0 5 0.1 6.4 0.3c1.4 0.2 2.8 0.6 4.1 1.3c1.4 0.8 2.5 1.7 3.1 2.9c0.6 1.2 1 2.6 1 4.2c0 1.9-0.5 3.4-1.4 4.7s-2.2 2.4-3.8 3.1v0.2c2.6 0.5 4.7 1.7 6.3 3.5C119.1 79 119.8 81.3 119.8 84z  M111.1 66.3c0-0.9-0.2-1.7-0.5-2.4c-0.3-0.6-0.8-1.2-1.5-1.6c-0.8-0.5-1.8-0.8-3-0.9c-1.2-0.1-2.6-0.2-4.4-0.2h-6.2v11.3h6.7c1.6 0 2.9-0.1 3.9-0.2s1.9-0.5 2.7-1c0.8-0.5 1.4-1.2 1.7-2C110.9 68.5 111.1 67.5 111.1 66.3z M114.4 84.2 c0-1.6-0.2-2.8-0.7-3.8c-0.5-0.9-1.3-1.7-2.6-2.4c-0.8-0.4-1.9-0.7-3.1-0.9c-1.2-0.1-2.7-0.2-4.4-0.2h-8.2v14.6h6.9 c2.3 0 4.2-0.1 5.6-0.4c1.5-0.2 2.6-0.7 3.6-1.3c1-0.7 1.7-1.5 2.2-2.3C114.2 86.7 114.4 85.6 114.4 84.2z M165.7 76.5c0 3.6-0.8 6.8-2.3 9.7c-1.6 2.9-3.6 5.1-6.2 6.7c-1.8 1.1-3.8 1.9-6 2.4 c-2.2 0.5-5.1 0.7-8.7 0.7h-9.9V56.9h9.8c3.8 0 6.9 0.3 9.1 0.8c2.3 0.6 4.2 1.3 5.7 2.3c2.7 1.7 4.7 3.9 6.2 6.7C165 69.4 165.7 72.7 165.7 76.5z M160.3 76.4c0-3.1-0.5-5.7-1.6-7.8c-1.1-2.1-2.7-3.8-4.8-5c-1.5-0.9-3.2-1.5-4.9-1.8 c-1.7-0.3-3.8-0.5-6.2-0.5h-4.9v30.2h4.9c2.5 0 4.7-0.2 6.6-0.6s3.6-1.1 5.1-2.1c1.9-1.2 3.4-2.9 4.4-4.9 C159.8 82 160.3 79.5 160.3 76.4z",
			path3: "M172.2 177.7l17.9-17.9c0.2-0.2 0.2-0.4 0-0.6l-17.9-17.9c-0.1-0.1-0.2-0.1-0.3-0.1H161 c-0.4 0-0.6 0.5-0.3 0.7l12.5 12.5c0.3 0.3 0.1 0.7-0.3 0.7h-39.7c-0.2 0-0.4 0.2-0.4 0.4v8c0 0.2 0.2 0.4 0.4 0.4h39.6 c0.4 0 0.6 0.5 0.3 0.7l-12.4 12.4c-0.3 0.3-0.1 0.7 0.3 0.7l10.9 0C172 177.8 172.1 177.7 172.2 177.7z M83.9 140.8l-18.2 18.2c0 0 0 0.1 0 0.1l18.2 18.2c0 0 0 0 0 0h11.9c0.1 0 0.1-0.1 0-0.1 l-13.7-13.7c0 0 0-0.1 0-0.1h40.9c0 0 0.1 0 0.1-0.1v-8.7c0 0 0-0.1-0.1-0.1H82.4c-0.1 0-0.1-0.1 0-0.1l13.6-13.6c0 0 0-0.1 0-0.1L83.9 140.8C84 140.8 83.9 140.8 83.9 140.8z",
			common: "M186.6 142.2c-9.5-3.6-21.1-9.7-28.6-16c-0.2-0.2-0.5 0-0.4 0.3c0.9 2.4 3.2 8.7 3.8 10.2 c0.1 0.2-0.1 0.3-0.2 0.3h-42.9c-0.1 0-0.3 0.1-0.3 0.3v9.5c0 0.1 0.1 0.3 0.3 0.3h42.9c0.2 0 0.3 0.2 0.2 0.3l-3.8 10.6 c-0.1 0.2 0.2 0.4 0.4 0.3c7.5-6.3 19.1-12 28.6-15.6C186.8 142.6 186.8 142.3 186.6 142.2z M98 158.9c-7.5 6.3-19.1 12.2-28.6 15.8c-0.2 0.1-0.2 0.4 0 0.5c9.5 3.6 21.1 9.5 28.6 15.8 c0.2 0.2 0.5 0 0.4-0.3c-0.9-2.4-3.2-8.9-3.8-10.4c-0.1-0.2 0.1-0.3 0.2-0.3h42.9c0.1 0 0.3-0.1 0.3-0.3v-9.5 c0-0.1-0.1-0.3-0.3-0.3H94.9c-0.2 0-0.3-0.2-0.2-0.3c0.6-1.5 2.9-8.1 3.8-10.5C98.5 159 98.2 158.8 98 158.9z",
			bubble: "M12.5 24.8L12.5 24.8c-6.8 0-12.3-5.5-12.3-12.2v0c0-6.8 5.5-12.2 12.2-12.2h0 c6.8 0 12.2 5.5 12.2 12.2v0C24.8 19.3 19.3 24.8 12.5 24.8z"
		}
	},
	contractl3 : {
		options : {
			editable : {tools : ["edit"]},
			width : 55,
			height : 55,
			type : "contractl3",
			path2:"M102.8 173h-4.6c-0.1 0-0.2 0.1-0.2 0.2v38.6c0 0.1 0.1 0.2 0.2 0.2h24.6c0.1 0 0.2-0.1 0.2-0.2v-3.6c0-0.1-0.1-0.2-0.2-0.2h-19.6c-0.1 0-0.2-0.1-0.2-0.2v-34.6C103 173.1 102.9 173 102.8 173z M157.3 196.2c-0.5-1.1-1.2-2.1-2.1-2.8c-0.8-0.7-1.5-1.2-2.6-1.7c-1.1-0.4-2.7-0.7-2.7-0.9v-0.4c2-0.4 3.7-1.4 5.2-3.1c1.5-1.6 2.2-3.5 2.2-5.7c0-1.6-0.4-3-1-4.2c-0.6-1.2-1.6-2.2-2.9-3.1c-1.2-0.8-2.5-.3-4-1.7 c-1.5-0.4-3.1-0.5-5.1-0.5c-1.9 0-4.2 0.3-6.3 0.8c-2.1 0.5-3.2 1.1-5.2 1.7v5.3h0.7c1.7-1 3.4-1.8 5.3-2.4s3.6-0.9 5.3-0.9 c1.1 0 2.1 0.1 2.9 0.3c0.9 0.2 1.7 0.5 2.4 1c0.7 0.5 1.3 1 1.7 1.8c0.4 0.7 0.6 1.5 0.6 2.5c0 2.1-0.8 3.9-2.4 5 c-1.6 1.1-3.7 1.8-6.4 1.8H141v4h2.8c1.3 0 2.5 0.2 3.6 0.4c1.1 0.2 2.1 0.6 2.9 1.2c0.8 0.5 1.4 1.3 1.9 2.2c0.4 0.9 0.7 2.1 0.7 3.5c0 1.4-0.2 2.6-0.6 3.5c-0.4 0.9-1 1.8-1.8 2.6c-0.8 0.7-1.8 1.3-3 1.6c-1.2 0.4-2.4 0.6-3.5 0.6 c-1.9 0-3.8-0.3-5.8-1c-2-0.6-3.8-1.5-5.3-2.5H132v5.5c2 0.6 3.3 1.2 5.4 1.7c2.1 0.5 4.3 0.8 6.5 0.8c2.1 0 4.1-0.3 5.9-0.9c1.8-0.6 3.3-1.5 4.7-2.7c1.2-1.1 2.1-2.4 2.7-3.9c0.6-1.5 1-3.1 1-4.9C158.2 198.8 157.9 197.3 157.3 196.2z M90.2 53h13.7c0.1 0 0.2 0.1 0.2 0.2v34.7c0 0.1 0.1 0.2 0.2 0.2h4.7c0.1 0 0.2-0.1 0.2-0.2V53.2c0-0.1 0.1-0.2 0.2-0.2h13.7c0.1 0 0.2-0.1 0.2-0.2v-3.7c0-0.1-0.1-0.2-0.2-0.2H90.2c-0.1 0-0.2 0.1-0.2 0.2v3.7 C90 52.9 90.1 53 90.2 53z M165.1 59.8c-0.8-2.5-2.1-4.7-3.7-6.4c-1.6-1.7-3.5-3-5.7-3.9c-2.2-0.9-4.8-1.4-7.6-1.4 c-2.7 0-5.2 0.5-7.5 1.4c-2.3 0.9-4.2 2.2-5.7 3.9c-1.6 1.8-2.8 4-3.7 6.5c-0.8 2.5-1.3 5.4-1.3 8.6c0 3.2 0.4 6.1 1.3 8.7 c0.8 2.5 2.1 4.7 3.6 6.4c1.6 1.7 3.5 3.1 5.8 4c2.3 0.9 4.8 1.4 7.5 1.4c2.8 0 5.3-0.4 7.5-1.3c2.2-0.9 4.1-2.2 5.7-4 c1.6-1.7 2.8-3.8 3.6-6.4c0.9-2.6 1.3-5.4 1.3-8.7S165.9 62.4 165.1 59.8z M157.5 80.3c-2.3 2.7-5.4 4.1-9.3 4.1 c-3.9 0-7-1.4-9.3-4.1c-2.3-2.7-3.5-6.7-3.5-11.8c0-5.1 1.1-9 3.4-11.8c2.3-2.8 5.4-4.1 9.4-4.1c3.9 0 7.1 1.4 9.3 4.1 c2.3 2.8 3.4 6.7 3.4 11.8C160.9 73.6 159.8 77.5 157.5 80.3z",
			path: "M128.1 253.8H128c-4.7 0-9.4-0.3-13.9-0.8l0.5-4.6c4.4 0.5 8.9 0.7 13.4 0.7L128.1 253.8z  M146.9 252.4l-0.7-4.6c4.5-0.7 9-1.6 13.3-2.8l1.2 4.5C156.2 250.7 151.5 251.7 146.9 252.4z M95.6 249.6c-4.5-1.2-9-2.7-13.4-4.4l1.7-4.3c4.2 1.7 8.5 3.1 12.9 4.2L95.6 249.6z M178.4 243.3l-1.9-4.2c4.1-1.8 8.2-3.9 12.1-6.1l2.3 4 C186.9 239.3 182.7 241.4 178.4 243.3z M65.2 237c-4-2.3-8-4.9-11.8-7.7l2.7-3.7c3.7 2.7 7.5 5.2 11.4 7.4L65.2 237z  M206.5 226.3l-2.9-3.6c3.5-2.8 6.9-5.9 10.1-9.1l3.3 3.3C213.7 220.2 210.1 223.4 206.5 226.3z M39.2 217.1c-3.3-3.3-6.5-6.9-9.4-10.5l3.6-2.9c2.8 3.5 5.9 6.9 9.1 10.1L39.2 217.1z M229.3 202.7l-3.7-2.7c2.7-3.6 5.2-7.5 7.4-11.4l4 2.3 C234.6 194.9 232 198.9 229.3 202.7z M19.1 191.1c-2.4-4.1-4.5-8.3-6.4-12.6l4.2-1.9c1.8 4.1 3.9 8.2 6.1 12.1L19.1 191.1z  M245.1 173.9l-4.3-1.7c1.7-4.2 3.1-8.5 4.2-12.9l4.5 1.2C248.3 165.1 246.9 169.6 245.1 173.9z M6.5 160.8c-1.2-4.5-2.2-9.1-2.9-13.8l4.6-0.7c0.7 4.5 1.6 9 2.8 13.3L6.5 160.8z M253 142.1l-4.6-0.5c0.5-4.5 0.8-9 0.8-13.6l0-0.5h4.6 l0 0.4C253.8 132.7 253.5 137.4 253 142.1z M2.2 128.3V128c0-4.6 0.3-9.3 0.8-13.8l4.6 0.5c-0.5 4.4-0.7 8.9-0.7 13.3L2.2 128.3zM247.8 109.5c-0.7-4.5-1.6-9-2.8-13.3l4.5-1.2c1.2 4.5 2.2 9.1 2.9 13.8L247.8 109.5z M10.9 96.9l-4.5-1.2 c1.2-4.5 2.7-9 4.4-13.4l4.3 1.7C13.4 88.2 12 92.5 10.9 96.9z M238.9 79.1c-1.8-4.1-3.9-8.2-6.2-12.1l4-2.3 c2.4 4.1 4.5 8.3 6.4 12.6L238.9 79.1z M22.9 67.7l-4-2.3c2.3-4.1 4.9-8 7.7-11.8l3.7 2.7C27.7 59.9 25.2 63.7 22.9 67.7z  M222.5 52.1c-2.8-3.5-5.9-6.9-9.1-10.1l3.3-3.3c3.3 3.3 6.5 6.8 9.4 10.5L222.5 52.1z M42.1 42.5l-3.3-3.3c3.3-3.3 6.8-6.5 10.5-9.4l2.9 3.6C48.7 36.3 45.3 39.3 42.1 42.5z M199.6 30.3c-3.6-2.7-7.5-5.2-11.4-7.4l2.3-4 c4.1 2.3 8.1 4.9 11.8 7.7L199.6 30.3z M67.1 23.2l-2.3-4c4.1-2.4 8.3-4.5 12.6-6.4l1.9 4.2C75.1 18.9 71 20.9 67.1 23.2z  M171.9 15c-4.2-1.6-8.6-3.1-12.9-4.2l1.2-4.5c4.5 1.2 9 2.7 13.4 4.4L171.9 15z M96.3 11l-1.2-4.5c4.5-1.2 9.2-2.2 13.8-2.9l0.7 4.6C105.1 8.9 100.7 9.8 96.3 11z M141.2 7.5c-4.3-0.5-8.8-0.7-13.2-0.7l-0.4-4.6h0.4c4.6 0 9.2 0.2 13.7 0.7L141.2 7.5z M232.5 227.4l-14.8-14.8c-0.1-0.1-0.3-0.1-0.5 0l-2.8 2.8c-0.1 0.1-0.1 0.3 0 0.5l14.8 14.8 c0.1 0.1 0.1 0.3 0 0.5l-4.9 4.9c-0.2 0.2-0.1 0.6 0.2 0.6l13.6 0c0.2 0 0.3-0.2 0.3-0.3l0-13.6c0-0.3-0.4-0.4-0.6-0.2l-4.9 4.9 C232.8 227.5 232.6 227.5 232.5 227.4z",
			path3:"M142.5 129c0.3 3 0.4 2 0.4 3.2c0 8.2-6.8 14.6-15.2 14.6s-16.6-6.7-16.6-14.9 c0-8.2 5.9-14.9 13.9-14.9v-4.8c-11 0-18.8 8.8-18.8 19.6c0 10.8 9.7 19.6 20.8 19.6c11.1 0 20.4-8.4 20.4-19.2c0-1.2 0.2-0.2 0-3.2H142.5z M140.5 110l-2.6 1.6c-0.2 0.1-0.1 0.4 0.1 0.4h6.3c0.2 0 0.3 0.2 0.1 0.4l-4.5 4.2c0 0 0-0.1 0-0.1 l-3.5 3.3l-5.5 5.3c-0.1 0.1-0.1 0.2 0 0.3l2.1 2c0.1 0.1 0.2 0.1 0.3 0l5.7-5.5l3.5-3.4l4.4-4.3c0.1-0.1 0.4 0 0.4 0.2l-0.2 6.2 c0 0.2 0.2 0.3 0.4 0.2l2.6-2.7c0 0 0.1-0.1 0.1-0.1v-7.6c0-0.1-0.1-0.2-0.2-0.2h-9.1C140.6 110 140.6 110 140.5 110z"
		}
	},
	shared : {
		options : {
			editable : {tools : ["edit"]},
			width : 55,
			height : 55,
			type : "shared",
			path : "M128 5C60.2 5 5 60.2 5 128s55.2 123 123 123s123-55.2 123-123S195.8 5 128 5z M128 245 c-64.6 0-117-52.4-117-117S63.4 11 128 11s117 52.4 117 117S192.6 245 128 245z",
			path2: "M67 74.8c-1.4-0.4-3.2-0.7-5.3-1.1c-2.1-0.4-3.7-0.6-4.8-0.9c-2.1-0.5-3.6-1.2-4.4-2.1 c-0.8-0.9-1.3-2.2-1.3-3.8c0-1.8 0.8-3.3 2.5-4.5c1.7-1.2 3.9-1.7 6.7-1.7c2.5 0 5 0.4 7.5 1.2c2.4 0.8 4.4 2.1 5.9 3.1H74v-5.9 c0-0.2-0.1-0.3-0.2-0.4c-1.9-0.7-3.5-1.3-5.7-1.9c-2.3-0.5-4.8-0.8-7.5-0.8c-4.3 0-7.7 1-10.5 3.1c-2.8 2.1-4.1 4.8-4.1 8.1 c0 2.9 0.8 5.2 2.5 7c1.7 1.8 4.3 3 7.7 3.8c1.7 0.4 3.3 0.6 4.9 0.9s3 0.5 4.3 0.9c1.7 0.5 3 1.1 3.9 1.9c0.8 0.8 1.3 2.1 1.3 3.8c0 2-0.9 3.6-2.7 4.8c-1.8 1.2-4.3 1.8-7.5 1.8c-2.3 0-4.7-0.5-7.3-1.4C50.3 90 48 89 46 87h0v6.4 c0 0.1 0.1 0.3 0.2 0.3c2 1 4.3 1.7 6.5 2.2c2.3 0.5 4.7 0.8 7.5 0.8c2.6 0 4.8-0.3 6.7-0.9c1.9-0.6 3.5-1.5 4.9-2.6 c1.3-1 2.3-2.3 3-3.8c0.7-1.5 1.1-3 1.1-4.5c0-2.8-0.7-5-2.2-6.6C72.2 76.7 70 75.6 67 74.8z M114.6 72H96.4c-0.2 0-0.4-0.2-0.4-0.4V57.4c0-0.2-0.2-0.4-0.4-0.4h-4.3c-0.2 0-0.4 0.2-0.4 0.4v38.3c0 0.2 0.2 0.4 0.4 0.4h4.3c0.2 0 0.4-0.2 0.4-0.4V77.4c0-0.2 0.2-0.4 0.4-0.4h18.3c0.2 0 0.4 0.2 0.4 0.4v18.3 c0 0.2 0.2 0.4 0.4 0.4h4.3c0.2 0 0.4-0.2 0.4-0.4V57.4c0-0.2-0.2-0.4-0.4-0.4h-4.3c-0.2 0-0.4 0.2-0.4 0.4v14.3 C115 71.8 114.8 72 114.6 72z M145.1 57.2l-14 38.3c-0.1 0.2 0.1 0.5 0.4 0.5h4.5c0.2 0 0.3-0.1 0.4-0.3l3.7-10.5 c0.1-0.2 0.2-0.3 0.4-0.3h16.4c0.2 0 0.3 0.1 0.4 0.3l3.7 10.5c0.1 0.2 0.2 0.3 0.4 0.3h4.8c0.3 0 0.4-0.3 0.4-0.5l-14-38.3c-0.1-0.1-0.2-0.2-0.4-0.2h-6.4C145.3 57 145.1 57.1 145.1 57.2z M141.8 80.5l6.4-17.7c0.1-0.3 0.6-0.3 0.7 0l6.3 17.7 c0.1 0.2-0.1 0.5-0.4 0.5h-12.7C141.9 81 141.7 80.7 141.8 80.5z M179.4 96h5.3c0.2 0 0.4-0.2 0.4-0.4v-6.3c0-0.2-0.2-0.4-0.4-0.4h-5.3c-0.2 0-0.4 0.2-0.4 0.4v6.3C179 95.8 179.2 96 179.4 96z M204 89.4v6.3c0 0.2 0.2 0.4 0.4 0.4h5.3c0.2 0 0.4-0.2 0.4-0.4v-6.3c0-0.2-0.2-0.4-0.4-0.4h-5.3 C204.2 89 204 89.2 204 89.4z M182.1 170.7c-8.3 4.6-19.7 8.8-31.1 10.6c-10.1 1.6-29.2-2.2-29.2-6.8c0-1.7 16.7 4.2 26.8 1.5 c7.7-2.1 6.6-9.3 2.4-10.3c-4.2-0.9-28.7-9.9-39.2-10.3c-4.6-0.1-15.4 1.5-21.6 2.5c-0.3-0.1-0.6-0.2-0.9-0.2l-18.4-1.1 c-1.8-0.1-3.5 1.2-4 2.8l-0.8 33.3c-0.4 1.6 0.6 2 2.4 2l15 2.8c0.3 0.4 0.7 0.6 1.4 0.2l1.1 0.1c1.8 0.1 3.3-0.7 3.4-2.4l0-0.5c1.7-0.6 3.5-0.8 5.2-0.4c5 1.1 48.4 9.1 54.4 7.7c8.6-2 32.8-16.4 37.4-20.8C193.8 174.7 188.2 167.4 182.1 170.7z",
			path3: "M182 149.7v-26.3c0-0.2-0.1-0.3-0.3-0.3h-47.3c-0.2 0-0.3 0.1-0.3 0.3v26.3c0 0.2-0.1 0.3-0.3 0.3h-2.3c-0.2 0-0.3 0.1-0.3 0.3v4.3c0 0.2 0.1 0.3 0.3 0.3h2.7h48h2.7c0.2 0 0.3-0.1 0.3-0.3v-4.3c0-0.2-0.1-0.3-0.3-0.3h-2.3 C182.1 150 182 149.9 182 149.7z M176.7 150h-37.3c-0.2 0-0.3-0.1-0.3-0.3v-21.3c0-0.2 0.1-0.3 0.3-0.3h37.3c0.2 0 0.3 0.1 0.3 0.3 v21.3C177 149.9 176.9 150 176.7 150z"
		}
	},
	contractshared : {
		options : {
			editable : {tools : ["edit"]},
			width : 55,
			height : 55,
			type : "contractshared",
			path : "M128 5C60.2 5 5 60.2 5 128s55.2 123 123 123s123-55.2 123-123S195.8 5 128 5z M128 245 c-64.6 0-117-52.4-117-117S63.4 11 128 11s117 52.4 117 117S192.6 245 128 245z",
			path2: "M67.1 170.8c-1.4-0.4-3.2-0.7-5.3-1.1c-2.1-0.4-3.7-0.6-4.8-0.9c-2.1-0.5-3.6-1.2-4.4-2.1 c-0.8-0.9-1.3-2.2-1.3-3.8c0-1.8 0.8-3.3 2.5-4.5c1.7-1.2 3.9-1.7 6.7-1.7c2.5 0 5 0.4 7.5 1.2c2.4 0.8 4.4 2.1 5.9 3.1h0v-5.9 c0-0.2-0.1-0.3-0.2-0.4c-1.9-0.7-3.5-1.3-5.7-1.9c-2.3-0.5-4.8-0.8-7.5-0.8c-4.3 0-7.7 1-10.5 3.1c-2.8 2.1-4.1 4.8-4.1 8.1 c0 2.9 0.8 5.2 2.5 7c1.7 1.8 4.3 3 7.7 3.8c1.7 0.4 3.3 0.6 4.9 0.9s3 0.5 4.3 0.9c1.7 0.5 3 1.1 3.9 1.9c0.8 0.8 1.3 2.1 1.3 3.8 c0 2-0.9 3.6-2.7 4.8c-1.8 1.2-4.3 1.8-7.5 1.8c-2.3 0-4.7-0.5-7.3-1.4C50.3 186 48 185 46 183h0v6.4c0 0.1 0.1 0.3 0.2 0.3 c2 1 4.3 1.7 6.5 2.2c2.3 0.5 4.7 0.8 7.5 0.8c2.6 0 4.8-0.3 6.7-0.9c1.9-0.6 3.5-1.5 4.9-2.6c1.3-1 2.3-2.3 3-3.8 c0.7-1.5 1.1-3 1.1-4.5c0-2.8-0.7-5-2.2-6.6C72.2 172.7 70 171.6 67.1 170.8z M119.7 153h-4.3c-0.2 0-0.4 0.2-0.4 0.4v14.3c0 0.2-0.1 0.4-0.3 0.4H96.4c-0.2 0-0.4-0.2-0.4-0.4 v-14.3c0-0.2-0.1-0.4-0.3-0.4h-4.3c-0.2 0-0.4 0.2-0.4 0.4v38.3c0 0.2 0.2 0.4 0.4 0.4h4.3c0.2 0 0.3-0.2 0.3-0.4v-18.3c0-0.2 0.2-0.4 0.4-0.4h18.3c0.2 0 0.3 0.2 0.3 0.4v18.3c0 0.2 0.2 0.4 0.4 0.4h4.3c0.2 0 0.3-0.2 0.3-0.4v-38.3 C120 153.2 119.9 153 119.7 153z M152.3 153.2c-0.1-0.1-0.2-0.2-0.4-0.2h-6.4c-0.2 0-0.3 0.1-0.4 0.2l-14 38.3 c-0.1 0.2 0.1 0.5 0.4 0.5h4.5c0.2 0 0.3-0.1 0.4-0.2l3.7-10.5c0.1-0.2 0.2-0.3 0.4-0.3h16.4c0.2 0 0.3 0.1 0.4 0.3l3.7 10.5 c0.1 0.2 0.2 0.2 0.4 0.2h4.8c0.3 0 0.4-0.3 0.4-0.5L152.3 153.2z M154.9 177h-12.7c-0.3 0-0.4-0.3-0.4-0.5l6.4-17.7 c0.1-0.3 0.6-0.3 0.7 0l6.3 17.7C155.3 176.7 155.2 177 154.9 177z M184.6 185h-5.3c-0.2 0-0.4 0.2-0.4 0.4v6.3c0 0.2 0.2 0.4 0.4 0.4h5.3c0.2 0 0.4-0.2 0.4-0.4v-6.3 C185 185.2 184.8 185 184.6 185z M209.6 185h-5.3c-0.2 0-0.4 0.2-0.4 0.4v6.3c0 0.2 0.2 0.4 0.4 0.4h5.3c0.2 0 0.4-0.2 0.4-0.4v-6.3C210 185.2 209.8 185 209.6 185z M122.8 53H90.3c-0.1 0-0.3 0.1-0.3 0.3v3.5c0 0.1 0.1 0.3 0.3 0.3h13.5c0.1 0 0.3 0.1 0.3 0.3v34.5 c0 0.1 0.1 0.3 0.3 0.3h4.5c0.1 0 0.3-0.1 0.3-0.3V57.3c0-0.1 0.1-0.3 0.3-0.3h13.5c0.1 0 0.3-0.1 0.3-0.3v-3.5 C123 53.1 122.9 53 122.8 53z M161.4 57.4c-1.6-1.7-3.5-3-5.7-3.9c-2.2-0.9-4.8-1.4-7.6-1.4c-2.7 0-5.2 0.5-7.5 1.4 c-2.3 0.9-4.2 2.2-5.8 3.9c-1.6 1.8-2.8 4-3.7 6.5c-0.8 2.5-1.3 5.4-1.3 8.6c0 3.2 0.4 6.1 1.3 8.7c0.8 2.5 2.1 4.7 3.6 6.4c1.6 1.7 3.5 3.1 5.7 4c2.3 0.9 4.8 1.4 7.5 1.4c2.8 0 5.3-0.4 7.5-1.3s4.1-2.2 5.7-4c1.6-1.7 2.8-3.8 3.6-6.4s1.3-5.4 1.3-8.7 s-0.4-6.1-1.3-8.7C164.2 61.3 163 59.1 161.4 57.4z M157.5 84.3c-2.3 2.7-5.4 4.1-9.3 4.1c-3.9 0-7-1.4-9.3-4.1 c-2.3-2.7-3.5-6.7-3.5-11.8c0-5.1 1.1-9 3.4-11.8c2.3-2.8 5.4-4.1 9.4-4.1c3.9 0 7.1 1.4 9.3 4.1c2.3 2.8 3.4 6.7 3.4 11.8 C160.9 77.6 159.8 81.5 157.5 84.3z",
			path3: "M167.9 117.5c-6.1 3.4-14.5 6.5-22.9 7.8c-7.5 1.2-21.5-1.6-21.5-5c0-1.3 12.3 3.1 19.7 1.1 c5.7-1.5 4.9-6.9 1.8-7.6c-3.1-0.7-21.2-7.3-28.9-7.5c-3.4-0.1-11.3 1.1-15.9 1.8c-0.2-0.1-0.4-0.1-0.7-0.1L86 107.2 c-1.3-0.1-2.6 0.9-2.9 2.1l-0.6 24.5c-0.3 1.2 0.5 1.5 1.8 1.5l11 2.1c0.2 0.3 0.5 0.4 1.1 0.1l0.8 0.1c1.3 0 2.4-0.5 2.5-1.8l0-0.4c1.2-0.4 2.5-0.6 3.8-0.3c3.7 0.8 35.6 6.7 40 5.7c6.3-1.5 24.1-12 27.6-15.3C176.4 120.5 172.3 115.1 167.9 117.5z"
		}
	}
})

angular.module('webappApp').constant("DIALOG_CONSTANTS", {
	/* Default values, max and min values dialog boxes */
	application : {
			maxNumInstances: 256,
			minNumInstances: 1,
	},
	tenant : {
		maxNumInstances: 256,
		minNumInstances: 1,
	},
	epg : {
		maxspan: 4,
		minspan: 1,
		maxsubnets: 256,
		minsubnets: 1,
		maxendpoints: 256,
		minendpoints: 1,
		
	},
	contracts: {
		minfilters: 1,
	},
	deleteTenant: {
		label: "Are you sure you want to delete ",
	},
	deleteApplication: {
		label: "Are you sure you want to delete ",
	},
	deleteLabel: {
		label: "Are you sure you want to delete ",
	},
	cloneProject: {
		label: "Are you sure you want to clone ",
	},
	room : {
		rowInstances : 1,
		minRowInstances : 1,
		maxRowInstances : 30,
		rackInstances : 1,
		minRackInstances : 1,
		maxRackInstances : 16
	},
	addDevice :{
		deviceInstances: 1
	}
})