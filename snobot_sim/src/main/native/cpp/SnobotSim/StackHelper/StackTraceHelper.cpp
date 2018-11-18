

#include "SnobotSim/StackHelper/StackTraceHelper.h"

#include "SnobotSim/Logging/SnobotLogger.h"

#ifdef _WIN32
#include <fstream>
#include <iostream>
#include <sstream>

#include "SnobotSim/StackHelper/StackWalker.h"
namespace StackTraceHelper
{
class MyStackWalker : public StackWalker
{
public:
    MyStackWalker() :
            mIsCallstackEntry(false)
    {
    }
    virtual void OnCallstackEntry(CallstackEntryType eType, CallstackEntry& entry)
    {
        mIsCallstackEntry = true;
        StackWalker::OnCallstackEntry(eType, entry);
        mIsCallstackEntry = false;
    }
    virtual void OnOutput(LPCSTR szText)
    {
        std::string message = szText;
        mFullStream << message;
        if (mIsCallstackEntry) // message.find("ERROR") == std::string::npos)
        {
            mFilteredStream << message;
        }
    }
    std::stringstream mFullStream;
    std::stringstream mFilteredStream;
    bool mIsCallstackEntry;
};
void PrintStackTracker()
{
    std::string fullDumpFilename = "callstack_dump.txt";
    MyStackWalker sw;
    sw.ShowCallstack();
    std::ofstream fullDump(fullDumpFilename);
    fullDump << sw.mFullStream.str() << std::endl;
    SNOBOT_LOG(SnobotLogging::LOG_LEVEL_CRITICAL,
            "\nDumping stack trace... Full windows trace can be seen at " << fullDumpFilename << ".\n\n"
                                                                          << sw.mFilteredStream.str())
}
/*
struct module_data {
    std::string image_name;
    std::string module_name;
    void *base_address;
    DWORD load_size;
};
typedef std::vector<module_data> ModuleList;
HANDLE thread_ready;
bool show_stack(std::ostream &, HANDLE hThread, CONTEXT& c);
DWORD __stdcall TargetThread( void *arg );
void ThreadFunc1();
void ThreadFunc2();
DWORD Filter( EXCEPTION_POINTERS *ep );
void *load_modules_symbols( HANDLE hProcess, DWORD pid );
int main( void ) {
    DWORD thread_id;
    thread_ready = CreateEvent( NULL, false, false, NULL );
    HANDLE thread = CreateThread( NULL, 0, TargetThread, NULL, 0, &thread_id );
    WaitForSingleObject( thread_ready, INFINITE );
    CloseHandle(thread_ready);
    return 0;
}
// if you use C++ exception handling: install a translator function
// with set_se_translator(). In the context of that function (but *not*
// afterwards), you can either do your stack dump, or save the CONTEXT
// record as a local copy. Note that you must do the stack dump at the
// earliest opportunity, to avoid the interesting stack-frames being gone
// by the time you do the dump.
DWORD Filter(EXCEPTION_POINTERS *ep) {
    HANDLE thread;
    DuplicateHandle(GetCurrentProcess(), GetCurrentThread(),
        GetCurrentProcess(), &thread, 0, false, DUPLICATE_SAME_ACCESS);
    std::cout << "Walking stack.";
    show_stack(std::cout, thread, *(ep->ContextRecord));
    std::cout << "\nEnd of stack walk.\n";
    CloseHandle(thread);
    return EXCEPTION_EXECUTE_HANDLER;
}
void ThreadFunc2() {
    __try { DebugBreak(); }
    __except (Filter(GetExceptionInformation())) {  }
    SetEvent(thread_ready);
}
void ThreadFunc1(void (*f)()) {
    f();
}
// We'll do a few levels of calls from our thread function so
//     there's something on the stack to walk...
//
DWORD __stdcall TargetThread(void *) {
    ThreadFunc1(ThreadFunc2);
    return 0;
}
class SymHandler {
    HANDLE p;
public:
    SymHandler(HANDLE process, char const *path=NULL, bool intrude = false) : p(process) {
        if (!SymInitialize(p, path, intrude))
            throw(std::logic_error("Unable to initialize symbol handler"));
    }
    ~SymHandler() { SymCleanup(p); }
};

#ifdef _M_X64
STACKFRAME64 init_stack_frame(CONTEXT c) {
    STACKFRAME64 s;
    s.AddrPC.Offset = c.Rip;
    s.AddrPC.Mode = AddrModeFlat;
    s.AddrStack.Offset = c.Rsp;
    s.AddrStack.Mode = AddrModeFlat;
    s.AddrFrame.Offset = c.Rbp;
    s.AddrFrame.Mode = AddrModeFlat;
    return s;
}
#else
STACKFRAME64 init_stack_frame(CONTEXT c) {
    STACKFRAME64 s;
    s.AddrPC.Offset = c.Eip;
    s.AddrPC.Mode = AddrModeFlat;
    s.AddrStack.Offset = c.Esp;
    s.AddrStack.Mode = AddrModeFlat;
    s.AddrFrame.Offset = c.Ebp;
    s.AddrFrame.Mode = AddrModeFlat;
    return s;
}
#endif

void sym_options(DWORD add, DWORD remove=0) {
    DWORD symOptions = SymGetOptions();
    symOptions |= add;
    symOptions &= ~remove;
    SymSetOptions(symOptions);
}
class symbol {
    typedef IMAGEHLP_SYMBOL64 sym_type;
    sym_type *sym;
    static const int max_name_len = 1024;
public:
    symbol(HANDLE process, DWORD64 address) : sym((sym_type *)::operator new(sizeof(*sym) + max_name_len)) {
        std::memset(sym, '\0', sizeof(*sym) + max_name_len);
        sym->SizeOfStruct = sizeof(*sym);
        sym->MaxNameLength = max_name_len;
        DWORD64 displacement;
        if (!SymGetSymFromAddr64(process, address, &displacement, sym))
            throw(std::logic_error("Bad symbol"));
    }
    std::string name() { return std::string(sym->Name); }
    std::string undecorated_name() {
        std::vector<char> und_name(max_name_len);
        UnDecorateSymbolName(sym->Name, &und_name[0], max_name_len, UNDNAME_COMPLETE);
        return std::string(&und_name[0], std::strlen(&und_name[0]));
    }
};
bool show_stack(std::ostream &os, HANDLE hThread, CONTEXT& c) {
    HANDLE process = GetCurrentProcess();
    int frame_number=0;
    DWORD offset_from_symbol=0;
    IMAGEHLP_LINE64 line = {0};
    SymHandler handler(process);
    sym_options(SYMOPT_LOAD_LINES | SYMOPT_UNDNAME);
    void *base = load_modules_symbols(process, GetCurrentProcessId());
    STACKFRAME64 s = init_stack_frame(c);
    line.SizeOfStruct = sizeof line;
    IMAGE_NT_HEADERS *h = ImageNtHeader(base);
    DWORD image_type = h->FileHeader.Machine;
    do {
        if (!StackWalk64(image_type, process, hThread, &s, &c, NULL, SymFunctionTableAccess64, SymGetModuleBase64, NULL))
            return false;
        os << std::setw(3) << "\n" << frame_number << "\t";
        if ( s.AddrPC.Offset != 0 ) {
            std::cout << symbol(process, s.AddrPC.Offset).undecorated_name();
            if (SymGetLineFromAddr64( process, s.AddrPC.Offset, &offset_from_symbol, &line ) )
                    os << "\t" << line.FileName << "(" << line.LineNumber << ")";
        }
        else
            os << "(No Symbols: PC == 0)";
        ++frame_number;
    } while (s.AddrReturn.Offset != 0);
    return true;
}
class get_mod_info {
    HANDLE process;
    static const int buffer_length = 4096;
public:
    get_mod_info(HANDLE h) : process(h) {}
    module_data operator()(HMODULE module) {
        module_data ret;
        char temp[buffer_length];
        MODULEINFO mi;
        GetModuleInformation(process, module, &mi, sizeof(mi));
        ret.base_address = mi.lpBaseOfDll;
        ret.load_size = mi.SizeOfImage;
        GetModuleFileNameEx(process, module, temp, sizeof(temp));
        ret.image_name = temp;
        GetModuleBaseName(process, module, temp, sizeof(temp));
        ret.module_name = temp;
        std::vector<char> img(ret.image_name.begin(), ret.image_name.end());
        std::vector<char> mod(ret.module_name.begin(), ret.module_name.end());
        SymLoadModule64(process, 0, &img[0], &mod[0], (DWORD64)ret.base_address, ret.load_size);
        return ret;
    }
};
void *load_modules_symbols(HANDLE process, DWORD pid) {
    ModuleList modules;
    DWORD cbNeeded;
    std::vector<HMODULE> module_handles(1);
    EnumProcessModules(process, &module_handles[0], module_handles.size() * sizeof(HMODULE), &cbNeeded);
    module_handles.resize(cbNeeded/sizeof(HMODULE));
    EnumProcessModules(process, &module_handles[0], module_handles.size() * sizeof(HMODULE), &cbNeeded);
    std::transform(module_handles.begin(), module_handles.end(), std::back_inserter(modules), get_mod_info(process));
    return modules[0].base_address;
}
*/
} // namespace StackTraceHelper
#else
namespace StackTraceHelper
{
void PrintStackTracker()
{
    LOG_UNSUPPORTED();
}
} // namespace StackTraceHelper
#endif
