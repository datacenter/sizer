angular.module('physical').constant("PHY_SIZER_CONSTANTS", {
	serverImage : {
		//path1 : 'M144 19H0.2C0.1 19 0 18.9 0 18.8V0.2C0 0.1 0.1 0 0.2 0H144c0.1 0 0.2 0.1 0.2 0.2v18.6C144.1 18.9 144.1 19 144 19z',
		path2 : 'M2.8,1v8.2H1V1H2.8 M3.7,0H0.1C0,0,0,0,0,0.1v10c0,0.1,0,0.1,0.1,0.1h3.6c0.1,0,0.1,0,0.1-0.1L3.7,0C3.8,0,3.8,0,3.7,0L3.7,0z M153.5,1v8.2h-1.8V1H153.5 M154.4,0h-3.6c-0.1,0-0.1,0-0.1,0.1v10c0,0.1,0,0.1,0.1,0.1h3.6c0.1,0,0.1,0,0.1-0.1L154.4,0C154.5,0,154.5,0,154.4,0L154.4,0z M18.8,1v2.1H17V1H18.8 M19.7,0h-3.6C16,0,16,0,16,0.1V4c0,0.1,0,0.1,0.1,0.1h3.6c0.1,0,0.1,0,0.1-0.1L19.7,0C19.8,0,19.8,0,19.7,0L19.7,0zM25.7,1v2.1h-1.8V1H25.7 M26.6,0H23c-0.1,0-0.1,0-0.1,0.1V4c0,0.1,0,0.1,0.1,0.1h3.6c0.1,0,0.1,0,0.1-0.1L26.6,0C26.7,0,26.7,0,26.6,0L26.6,0z M18.8,7.1v2.1H17V7.1H18.8 M19.7,6.1h-3.6c-0.1,0-0.1,0-0.1,0.1v3.9c0,0.1,0,0.1,0.1,0.1h3.6c0.1,0,0.1,0,0.1-0.1L19.7,6.1C19.8,6.1,19.8,6.1,19.7,6.1L19.7,6.1z M25.7,7.1v2.1h-1.8V7.1H25.7 M26.6,6.1H23c-0.1,0-0.1,0-0.1,0.1v3.9c0,0.1,0,0.1,0.1,0.1h3.6c0.1,0,0.1,0,0.1-0.1L26.6,6.1C26.7,6.1,26.7,6.1,26.6,6.1L26.6,6.1z M129.5,1v2.1h-1.8V1H129.5 M130.4,0h-3.6c-0.1,0-0.1,0-0.1,0.1V4c0,0.1,0,0.1,0.1,0.1h3.6c0.1,0,0.1,0,0.1-0.1L130.4,0C130.5,0,130.5,0,130.4,0L130.4,0z M136.4,1v2.1h-1.8V1H136.4 M137.3,0h-3.6c-0.1,0-0.1,0-0.1,0.1V4c0,0.1,0,0.1,0.1,0.1h3.6c0.1,0,0.1,0,0.1-0.1L137.3,0C137.4,0,137.3,0,137.3,0L137.3,0z M129.5,7.1v2.1h-1.8V7.1H129.5 M130.4,6.1h-3.6c-0.1,0-0.1,0-0.1,0.1v3.9c0,0.1,0,0.1,0.1,0.1h3.6c0.1,0,0.1,0,0.1-0.1L130.4,6.1C130.5,6.1,130.5,6.1,130.4,6.1L130.4,6.1z M136.4,7.1v2.1h-1.8V7.1H136.4 M137.3,6.1h-3.6c-0.1,0-0.1,0-0.1,0.1v3.9c0,0.1,0,0.1,0.1,0.1h3.6c0.1,0,0.1,0,0.1-0.1L137.3,6.1C137.4,6.1,137.3,6.1,137.3,6.1L137.3,6.1z'
	},

	switchImage : {
		path1 : 'M20.3,1H4.4c0,0-0.1,0-0.1,0.1v15.8c0,0,0,0.1,0.1,0.1h15.9c0,0,0.1,0,0.1-0.1L20.3,1C20.4,1,20.3,1,20.3,1z M19.3,15.9C19.3,16,19.3,16,19.3,15.9L5.4,16c0,0-0.1,0-0.1-0.1V2.1c0,0,0-0.1,0.1-0.1h13.8c0,0,0.1,0,0.1,0.1V15.9z M16.9,6h-2.3v0.8c0,0.1-0.1,0.1-0.1,0.1h1.5c0.1,0,0.1,0.1,0.1,0.1v6c0,0.1-0.1,0.1-0.1,0.1H8.7c-0.1,0-0.1-0.1-0.1-0.1V7c0-0.1,0.1-0.1,0.1-0.1h1.5c-0.1,0-0.1-0.1-0.1-0.1V6H7.8C7.7,6,7.6,6,7.6,6.1V14c0,0.1,0.1,0.1,0.1,0.1h9.1c0.1,0,0.1-0.1,0.1-0.1V6.1C17,6,16.9,6,16.9,6z M11,5.8V5c0-0.1,0.1-0.1,0.1-0.1h2.4c0.1,0,0.1,0.1,0.1,0.1v0.9c0,0.1-0.1,0.1-0.1,0.1h1.1V4c0-0.1-0.1-0.1-0.1-0.1h-4.3c-0.1,0-0.1,0.1-0.1,0.1V6h1.1C11.1,6,11,5.9,11,5.8z M10.2,6.9h4.3c0.1,0,0.1-0.1,0.1-0.1V6h-1.1h-2.4h-1.1v0.8C10.1,6.8,10.1,6.9,10.2,6.9z M155.5,1h-15.9c0,0-0.1,0-0.1,0.1v15.8c0,0,0,0.1,0.1,0.1h15.9c0,0,0.1,0,0.1-0.1L155.5,1C155.6,1,155.6,1,155.5,1z M154.6,15.9C154.6,16,154.5,16,154.6,15.9L140.7,16c0,0-0.1,0-0.1-0.1V2.1c0,0,0-0.1,0.1-0.1h13.8c0,0,0.1,0,0.1,0.1V15.9z M152.1,6h-2.3v0.8c0,0.1-0.1,0.1-0.1,0.1h1.5c0.1,0,0.1,0.1,0.1,0.1v6c0,0.1-0.1,0.1-0.1,0.1h-7.3c-0.1,0-0.1-0.1-0.1-0.1V7c0-0.1,0.1-0.1,0.1-0.1h1.5c-0.1,0-0.1-0.1-0.1-0.1V6H143c-0.1,0-0.1,0.1-0.1,0.1V14c0,0.1,0.1,0.1,0.1,0.1h9.1c0.1,0,0.1-0.1,0.1-0.1V6.1C152.3,6,152.2,6,152.1,6zM146.3,5.8V5c0-0.1,0.1-0.1,0.1-0.1h2.4c0.1,0,0.1,0.1,0.1,0.1v0.9c0,0.1-0.1,0.1-0.1,0.1h1.1V4c0-0.1-0.1-0.1-0.1-0.1h-4.3c-0.1,0-0.1,0.1-0.1,0.1V6h1.1C146.3,6,146.3,5.9,146.3,5.8zM145.4,6.9h4.3c0.1,0,0.1-0.1,0.1-0.1V6h-1.1h-2.4h-1.1v0.8C145.3,6.8,145.4,6.9,145.4,6.9z',
	 	path2 : 'M8.7,6.9C8.6,6.9,8.6,6.9,8.6,7v6c0,0.1,0.1,0.1,0.1,0.1h7.3c0.1,0,0.1-0.1,0.1-0.1V7c0-0.1-0.1-0.1-0.1-0.1h-1.5h-4.3H8.7z M13.6,5.8V5c0-0.1-0.1-0.1-0.1-0.1h-2.4C11.1,4.8,11,4.9,11,5v0.9C11,5.9,11.1,6,11.1,6h2.4C13.6,6,13.6,5.9,13.6,5.8z M19.2,2H5.4c0,0-0.1,0-0.1,0.1v13.8c0,0,0,0.1,0.1,0.1h13.8c0,0,0.1,0,0.1-0.1L19.2,2C19.3,2.1,19.3,2,19.2,2z M17,14c0,0.1-0.1,0.1-0.1,0.1H7.8c-0.1,0-0.1-0.1-0.1-0.1V6.1C7.6,6,7.7,6,7.8,6h2.3V4c0-0.1,0.1-0.1,0.1-0.1h4.3c0.1,0,0.1,0.1,0.1,0.1V6h2.3C16.9,6,17,6,17,6.1V14z M143.9,6.9c-0.1,0-0.1,0.1-0.1,0.1v6c0,0.1,0.1,0.1,0.1,0.1h7.3c0.1,0,0.1-0.1,0.1-0.1V7c0-0.1-0.1-0.1-0.1-0.1h-1.5h-4.3H143.9z M148.9,5.8V5c0-0.1-0.1-0.1-0.1-0.1h-2.4c-0.1,0-0.1,0.1-0.1,0.1v0.9c0,0.1,0.1,0.1,0.1,0.1h2.4C148.8,6,148.9,5.9,148.9,5.8z M154.5,2h-13.8c0,0-0.1,0-0.1,0.1v13.8c0,0,0,0.1,0.1,0.1h13.8c0,0,0.1,0,0.1-0.1L154.5,2C154.6,2.1,154.5,2,154.5,2z M152.3,14c0,0.1-0.1,0.1-0.1,0.1H143c-0.1,0-0.1-0.1-0.1-0.1V6.1c0-0.1,0.1-0.1,0.1-0.1h2.3V4c0-0.1,0.1-0.1,0.1-0.1h4.3c0.1,0,0.1,0.1,0.1,0.1V6h2.3c0.1,0,0.1,0.1,0.1,0.1V14z'
//		path3 : 'M10.2 10.6H5.8c-0.1 0-0.2-0.1-0.2-0.2V6.8c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C10.4 10.5 10.3 10.6 10.2 10.6zM17.2 10.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2V6.8c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C17.3 10.5 17.2 10.6 17.2 10.6zM24.1 10.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2V6.8c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6	C24.2 10.5 24.2 10.6 24.1 10.6zM31 10.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2V6.8c0-0.1 0.1-0.2 0.2-0.2H31c0.1 0 0.2 0.1 0.2 0.2v3.6C31.2 10.5 31.1 10.6 31 10.6z M37.9 10.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2V6.8c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C38.1 10.5 38 10.6 37.9 10.6z M44.9 10.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2V6.8c0-0.1 0.1-0.2 0.2-0.2h4.4C45 6.6 45 6.7 45 6.8v3.6C45 10.5 45 10.6 44.9 10.6zM51.8 10.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2V6.8c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C52 10.5 51.9 10.6 51.8 10.6zM58.7 10.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2V6.8c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C58.9 10.5 58.8 10.6 58.7 10.6zM65.6 10.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2V6.8c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C65.8 10.5 65.7 10.6 65.6 10.6zM72.6 10.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2V6.8c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C72.7 10.5 72.7 10.6 72.6 10.6zM79.5 10.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2V6.8c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C79.7 10.5 79.6 10.6 79.5 10.6zM86.4 10.6H82c-0.1 0-0.2-0.1-0.2-0.2V6.8c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C86.6 10.5 86.5 10.6 86.4 10.6zM93.4 10.6H89c-0.1 0-0.2-0.1-0.2-0.2V6.8c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C93.5 10.5 93.4 10.6 93.4 10.6zM100.3 10.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2V6.8c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C100.4 10.5 100.4 10.6 100.3 10.6zM107.2 10.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2V6.8c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C107.4 10.5 107.3 10.6 107.2 10.6zM114.1 10.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2V6.8c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C114.3 10.5 114.2 10.6 114.1 10.6zM121.1 10.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2V6.8c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6	C121.2 10.5 121.2 10.6 121.1 10.6zM10.2 16.8H5.8c-0.1 0-0.2-0.1-0.2-0.2V13c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6	C10.4 16.7 10.3 16.8 10.2 16.8z M10.2 22.6H5.8c-0.1 0-0.2-0.1-0.2-0.2v-3.6c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C10.4 22.5 10.3 22.6 10.2 22.6z M17.2 16.8h-4.4c-0.1 0-0.2-0.1-0.2-0.2V13c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C17.3 16.7 17.2 16.8 17.2 16.8z M17.2 22.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2v-3.6c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C17.3 22.5 17.2 22.6 17.2 22.6z M24.1 16.8h-4.4c-0.1 0-0.2-0.1-0.2-0.2V13c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C24.2 16.7 24.2 16.8 24.1 16.8z M24.1 22.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2v-3.6c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C24.2 22.5 24.2 22.6 24.1 22.6z M31 16.8h-4.4c-0.1 0-0.2-0.1-0.2-0.2V13c0-0.1 0.1-0.2 0.2-0.2H31c0.1 0 0.2 0.1 0.2 0.2v3.6C31.2 16.7 31.1 16.8 31 16.8zM31 22.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2v-3.6c0-0.1 0.1-0.2 0.2-0.2H31c0.1 0 0.2 0.1 0.2 0.2v3.6C31.2 22.5 31.1 22.6 31 22.6zM37.9 16.8h-4.4c-0.1 0-0.2-0.1-0.2-0.2V13c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C38.1 16.7 38 16.8 37.9 16.8zM37.9 22.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2v-3.6c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C38.1 22.5 38 22.6 37.9 22.6zM44.9 16.8h-4.4c-0.1 0-0.2-0.1-0.2-0.2V13c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C45 16.7 45 16.8 44.9 16.8zM44.9 22.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2v-3.6c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C45 22.5 45 22.6 44.9 22.6zM51.8 16.8h-4.4c-0.1 0-0.2-0.1-0.2-0.2V13c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C52 16.7 51.9 16.8 51.8 16.8zM51.8 22.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2v-3.6c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C52 22.5 51.9 22.6 51.8 22.6zM58.7 16.8h-4.4c-0.1 0-0.2-0.1-0.2-0.2V13c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C58.9 16.7 58.8 16.8 58.7 16.8zM58.7 22.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2v-3.6c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C58.9 22.5 58.8 22.6 58.7 22.6zM65.6 16.8h-4.4c-0.1 0-0.2-0.1-0.2-0.2V13c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C65.8 16.7 65.7 16.8 65.6 16.8zM65.6 22.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2v-3.6c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C65.8 22.5 65.7 22.6 65.6 22.6zM72.6 16.8h-4.4c-0.1 0-0.2-0.1-0.2-0.2V13c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C72.7 16.7 72.7 16.8 72.6 16.8zM72.6 22.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2v-3.6c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C72.7 22.5 72.7 22.6 72.6 22.6zM79.5 16.8h-4.4c-0.1 0-0.2-0.1-0.2-0.2V13c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C79.7 16.7 79.6 16.8 79.5 16.8zM79.5 22.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2v-3.6c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C79.7 22.5 79.6 22.6 79.5 22.6zM86.4 16.8H82c-0.1 0-0.2-0.1-0.2-0.2V13c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C86.6 16.7 86.5 16.8 86.4 16.8zM86.4 22.6H82c-0.1 0-0.2-0.1-0.2-0.2v-3.6c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C86.6 22.5 86.5 22.6 86.4 22.6zM93.4 16.8H89c-0.1 0-0.2-0.1-0.2-0.2V13c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C93.5 16.7 93.4 16.8 93.4 16.8zM93.4 22.6H89c-0.1 0-0.2-0.1-0.2-0.2v-3.6c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C93.5 22.5 93.4 22.6 93.4 22.6zM100.3 16.8h-4.4c-0.1 0-0.2-0.1-0.2-0.2V13c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6	C100.4 16.7 100.4 16.8 100.3 16.8z M100.3 22.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2v-3.6c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C100.4 22.5 100.4 22.6 100.3 22.6z M107.2 16.8h-4.4c-0.1 0-0.2-0.1-0.2-0.2V13c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C107.4 16.7 107.3 16.8 107.2 16.8z M107.2 22.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2v-3.6c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C107.4 22.5 107.3 22.6 107.2 22.6z M114.1 16.8h-4.4c-0.1 0-0.2-0.1-0.2-0.2V13c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C114.3 16.7 114.2 16.8 114.1 16.8z M114.1 22.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2v-3.6c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C114.3 22.5 114.2 22.6 114.1 22.6z M121.1 16.8h-4.4c-0.1 0-0.2-0.1-0.2-0.2V13c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C121.2 16.7 121.2 16.8 121.1 16.8z M121.1 22.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2v-3.6c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C121.2 22.5 121.2 22.6 121.1 22.6z M145.8 10.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2V6.8c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C146 10.5 145.9 10.6 145.8 10.6z M152.7 10.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2V6.8c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C152.9 10.5 152.8 10.6 152.7 10.6z M145.8 16.8h-4.4c-0.1 0-0.2-0.1-0.2-0.2V13c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C146 16.7 145.9 16.8 145.8 16.8z M145.8 22.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2v-3.6c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C146 22.5 145.9 22.6 145.8 22.6z M152.7 16.8h-4.4c-0.1 0-0.2-0.1-0.2-0.2V13c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C152.9 16.7 152.8 16.8 152.7 16.8z M152.7 22.6h-4.4c-0.1 0-0.2-0.1-0.2-0.2v-3.6c0-0.1 0.1-0.2 0.2-0.2h4.4c0.1 0 0.2 0.1 0.2 0.2v3.6C152.9 22.5 152.8 22.6 152.7 22.6z'
	}, 

	deviceType: {
		server: 0,
		switch: 1,
		fabricInterconnect:2,
		firewall:3
	},
	rowTerminationLabel: "Are you sure you want to revert?",
	rowTerminationSuccessful: "Port Termination successful",
	rowTerminationFailure: "There are some racks NOT terminated. Please check the details",
	rowRevertSuccess: "Automatically placed leaves are removed",
	roomTerminationSuccessful:"Row Termination successful",
	spineTerminationSuccessful:"Spine Termination successful",
	roomRevertSuccess: "Switches automatically placed in last termination are removed",
	DOWN: 0,
	UP: 1
})