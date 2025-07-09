import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CreateJobComponent } from './create-job.component';

describe('CreateJobComponent', () => {
  let component: CreateJobComponent;
  let fixture: ComponentFixture<CreateJobComponent>;
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [CreateJobComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(CreateJobComponent);
    component = fixture.componentInstance;
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should toggle the form display and button text on openCloseForm() call', () => {
    // Initial state
    expect(component.showForm).toBe(false);
    expect(component.buttonText).toBe('Créer une annonce');

    component.openCloseForm();

    expect(component.showForm).toBe(true);
    expect(component.buttonText).toBe('Annuler');
  });

  it('should close the form and clear suggestions on closeForm() call', () => {
    component.suggestions = ['Paris', 'Lyon'];
    component.closeForm();
    expect(component.showForm).toBe(false);
    expect(component.suggestions.length).toBe(0);
  });

  it('should log form data to console and reset the form on submitForm() call', () => {
    spyOn((window as any).console, 'log');
    const initialForm = { ...component.form };
    component.submitForm();
    expect(console.log).toHaveBeenCalledWith('Annonce créée :', initialForm);
  });

  it('should fetch suggestions based on location input', () => {
    component.onLocationInput({ target: { value: 'Paris' } });
    const req = httpMock.expectOne(`https://geo.api.gouv.fr/communes?nom=Paris&fields=nom&boost=population&limit=5`);
    expect(req.request.method).toBe('GET');
    req.flush([{ nom: 'Paris' }, { nom: 'Versailles' }]);

    expect(component.suggestions).toEqual(['Paris', 'Versailles']);
  });

  it('should select a suggestion and clear other suggestions', () => {
    component.form.location = '';
    component.selectSuggestion('Paris');
    expect(component.form.location).toBe('Paris');
    expect(component.suggestions.length).toBe(0);
  });
});

