package com.murawski.library.init;

import com.murawski.library.model.AddCopyCommand;
import com.murawski.library.service.CopyService;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class CopyInit {

    private CopyService copyService;

    @Inject
    public CopyInit(CopyService copyService) {
        this.copyService = copyService;
    }

    public void addFirstCopies(@Observes StartupEvent startupEvent) {
        copyService.addCopy(new AddCopyCommand("978-83-24655-64-9", 5));
        copyService.addCopy(new AddCopyCommand("978-83-28302-34-1", 3));
        copyService.addCopy(new AddCopyCommand("978-83-28335-21-9", 1));
    }
}
