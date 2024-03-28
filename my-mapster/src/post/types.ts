export interface IPostCreate {
    title: string;
    file: File|undefined;
    content: string;
}

export interface IUploadedFile {
    originFileObj: File
}
export interface IPostItem {
    id: number;
    title: string;
    content: string;
    image: string;
}

export interface IPostEdit {
    id: number;
    title: string;
    file: File|undefined;
    content: string;
}