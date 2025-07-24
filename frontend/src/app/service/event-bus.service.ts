import { Injectable } from '@angular/core';
import { Subject, Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EventData } from './event.class';

@Injectable({
  providedIn: 'root'
})
export class EventBusService {
  private subject$ = new Subject<EventData>();

  constructor() { }

  /**
   * Emits an event on the event bus.
   * @param event The event to emit.
   */
  emit(event: EventData) {
    this.subject$.next(event);
  }

  /**
   * Subscribes to a specific event.
   * @param eventName The name of the event to subscribe to.
   * @param action The action to perform when the event is emitted.
   * @returns A subscription object that can be used to unsubscribe.
   */
  on(eventName: string, action: (payload: any) => void): Subscription {
    return this.subject$.pipe(
      filter((e: EventData) => e.name === eventName),
      map((e: EventData) => e.value)
    ).subscribe(action);
  }
}
