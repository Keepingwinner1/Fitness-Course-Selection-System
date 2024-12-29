interface Coachinfo{
    coachID:number,
    userID:number,
    realName:string,
    number:string,
    phone:string,
    address:string,
    cardID:string,
    gymID:number
}
interface book{
    bookID:number,
    classID:number,
    traineeID:number,
    paymentID:number,
    payMethod:string,
    bookStatus:boolean,
    bookTime:string,
}
interface refund{
    paymentID:number,
    bookIDs:string,
    amount:number,
    payMethod:string,
    paymentStatus:number,
    payTime:string,
}
interface pay{
    paymentID:number,
    bookIDs:string,
    amount:number,
    payMethod:string,
    paymentStatus:number,
    payTime:string,
}
interface ClassCreate{
    classID: number,
    courseType: string,
    courseName: string,
    courseDescription:string,
    courseGrade: number,
    coursePhotoUrl: string,
    capacity: number,
    courseStartTime: string,
    courseEndTime: string,
    dayofWeek:string,
}
interface gym{
    gymID:number,
    gymName:string,
    address:string,
    createTime:string;
}

interface myClass{
    classId: number;
    courseType: string;
    courseName: string;
    courseDescription: string;
    courseGrade: number | null;
    coursePhotoUrl: string;
    capacity: number;
    coursePrice: number;
    courseStartTime: string;
    courseEndTime: string;
    dayOfWeek   : string;
    bookId: number | null;
}

interface User{
    userID: number,
    userName: string,
    password: string,
    email:string,
    registrationTime: Date,
    age: number,
    gender: string,
    iconURL: string,
    tags: string,
    introduction: string,
    goalType: string,
    goalWeight: string,
    type: string,
    token:string,
}

interface Coach{
    coachID:number,
    userID:number,
    iconURL: string,
    realName:string,
    phone:string,
    number:string,
    address:string,
    cardID:string,
    gymID:number,
    registerTime:Date,
    status: number,
    token: string,
}

interface Profile{
    userID: number,
    userName:string,
    email:string,
    age: number,
    gender: string,
    iconURL: string,
    tags: string,
    introduction: string,
    goalType: string,
    goalWeight: string,
}

interface Gym{
    gymID:number,
    gymName:string,
    address:string,
    createTime:Date,
}

interface Admin{
    id:number,
    userID:number,
    gymID:number,
    iconURL:string,
    token:string,
}

interface Course{
    courseId:number,
    courseType:string,
    courseName:string,
    courseDescription:string,
    coursePhotoUrl:string,
    courseGrade:number,
}