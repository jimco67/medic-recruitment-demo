export interface JobOffer {
  id: number;
  title: string;
  description: string;
  location: string;
  speciality: string;
  recruiterId: number;
  postedDate: string; // Or Date, depending on the backend format
}
