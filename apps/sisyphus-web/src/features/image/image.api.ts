import api from '@/services/route';
import imageCompression from 'browser-image-compression';
import type { UploadImageResponse } from './image.type';

const compressImage = (file: File) =>
  imageCompression(file, {
    maxSizeMB: 1,
    maxWidthOrHeight: 1280,
    useWebWorker: true,
  });

/**
 * @param file {File} 업로드할 원본 이미지 파일
 * @returns {Promise<File>} 압축된 이미지 파일
 */
async function toCompressedFile(file: File): Promise<File> {
  const compressedBlob = await compressImage(file);
  return new File([compressedBlob], file.name, { type: compressedBlob.type });
}

/**
 * @param file {File} 업로드할 이미지 파일
 * @returns {FormData} multipart/form-data payload
 */
async function buildImageFormData(file: File): Promise<FormData> {
  if (!file || !file.type.startsWith('image/')) {
    throw new Error('이미지 파일만 업로드할 수 있습니다.');
  }

  const compressedFile = await toCompressedFile(file);
  const formData = new FormData();
  formData.append('file', compressedFile); // @RequestParam("file")
  return formData;
}

/** 이미지 업로드 */
export async function uploadImage(file: File): Promise<UploadImageResponse> {
  const formData = await buildImageFormData(file);
  const { data } = await api.post<UploadImageResponse>('/image', formData);
  return data;
}

/** 이미지 교체(수정) */
export async function updateImage(
  file: File,
  id: number,
): Promise<UploadImageResponse> {
  const formData = await buildImageFormData(file);
  const { data } = await api.put<UploadImageResponse>(`/image/${id}`, formData);
  return data;
}
